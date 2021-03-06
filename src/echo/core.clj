(ns echo.core
  ( :use [clojure.java.shell :only [sh]]
   
   :require [clojure.string :as str]
           [clj-http.client :as client] 
           [cheshire.core :refer :all :as json])
    (:import [java.io BufferedReader StringReader Reader] 
  [javax.sound.sampled AudioSystem LineEvent$Type LineListener]  [java.net URL]) 
  (:gen-class))


(defn- subtitles-read
    [reader]
    (letfn [(parse-timing [s]
                          (let [nums (map #(Integer/parseInt %) (str/split s #":|,"))]
                            (reduce + (map * nums [(* 60 60 1000) (* 60 1000) 1000 1] ))))]

          (for [[ctr tmng & txt] (->> (java.io.BufferedReader. reader)
                                                                      (line-seq)
                                                                      (partition-by (partial = ""))
                                                                      (remove (partial = [""])))
                          :let [counter (Integer/parseInt (re-find #"\d+" ctr))
                                                [_ strt nd] (re-find #"([^\s]+) --> ([^\s]+)" tmng)
                                                start (parse-timing strt)
                                                end (parse-timing nd)]]

                  {:counter counter
                          :start start
                          :end end
                          :text (str/join txt)})))


(defprotocol Read-Subtitles-From
    (read-subtitles-from [input]))


(extend-protocol Read-Subtitles-From
    String
    (read-subtitles-from [s]
          (subtitles-read (java.io.StringReader. s)))

    java.io.Reader
    (read-subtitles-from [reader]
          (subtitles-read reader)))


(defn read-subtitles
    "Reads subtitle data from srt format into a lazy-sequence of maps"
    [input]
    (read-subtitles-from input))


;(print (read-subtitles (slurp "foo.srt")))

;; HTTP stuff
(defn fetch-json [url]
  (json/parse-string (slurp url)))
;; YouTube API
(def api-key "AIzaSyCMJUwbh2KMuvZHlfbuIb8sAfQUyPMzMYg")

(def info-url "https://www.googleapis.com/youtube/v3/videos?key=%s&part=id%%2Csnippet%%2CcontentDetails&id=%s")

(def query-url "https://www.googleapis.com/youtube/v3/search?key=%s&part=snippet&q=%s")

(defn search-video [q]
  (fetch-json (format query-url api-key q)))

(defn fetch-video-info [id]
  (fetch-json (format info-url api-key id)))


(def search-cc (comp #(clojure.core/get % "items") search-video #(str % ",cc")))

(defn dl [id] ((println id) (sh  "youtube-dl" id "-o" "%(id)s.wav" "--audio-format" "wav" "--extract-audio")))
(def srt-url (partial format "http://video.google.com/timedtext?lang=en&v=%s&fmt=srt"))

(def index-es #(client/post "http://localhost:9200/subtitles/srt"
  { :body (str %) :content-type :json :accept :json}))

(def fix-period #(+ (.getMillis %) (* 1000 (.getSeconds %)) )) 
(defn fix-subs [m] (update-in m [:start :end] fix-period)) 
(def read-srt (comp #(map fix-subs %) read-subtitles slurp)) 
(defn dl-query [q]
  (let [infos (search-cc q) 
        videos (filter #(get-in % ["id" "videoId"]) infos)
        ids (map #(get-in % ["id" "videoId"]) videos )]
;(dorun (pmap dl ids))
(->> ids
 (map srt-url)
 (map slurp)
 (map read-subtitles)
 (mapcat (fn [id sts]
        (map #(merge % {:id (str id "-" (:counter %) ) :videoId id}) sts)) ids)
     ;(map solr-add)
     ;(map index-es)
(map json/generate-string)
(map index-es)
    dorun)))
 
;it would be simpler to save in solr.
;sudo apt-get install libav-tools => needed to extract audio


;; Various options:
;(def index-srt "add srt file to solr." (comp solr-add read-srt))


(defn write-json [m p] (json/generate-stream m (clojure.java.io/writer p) {:pretty true}))

(defn srt->json [p] (let [srt-map (read-srt p)
                          json-path (str  (reverse (str/replace-first (reverse ".srt" (reverse p))))  ".json")]
                      (write-json srt-map json-path)
                      json-path))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  
  (println "Hello, World!")
  (dl-query (first args)) )

;(defn simple-play
; [url]
; (let [stream (AudioSystem/getAudioInputStream url)
;       clip   (AudioSystem/getClip)]
;    (.addLineListener clip
;      (proxy[LineListener] []
;        (update [event]
;          (when (and event
;                     (= (.getType event)
;                        (LineEvent$Type/STOP)))
;
;             (.close clip)
;             (.close stream)))))
;    (try
;      (.open clip stream)
;      (.start clip)
;      (catch Exception e nil))))
;
;(simple-play "~/music/samples/04-night-beat-50-06-05-018-the-girl-from-kansas.wav")
