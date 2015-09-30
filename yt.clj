(ns fetaoin.core
  (:require [irclj.core :as irc]
            [clojure.data.json :as json]))

;; HTTP stuff
(defn fetch-json [url]
  (json/read-str (slurp url)))

;; YouTube API
(def api-key "sekret")

(def info-url "https://www.googleapis.com/youtube/v3/videos?key=%s&part=id%%2Csnippet%%2CcontentDetails&id=%s")

(defn get-id [video-info]
  (get-in video-info ["items" 0 "id"]))

(defn get-title [video-info]
  (get-in video-info ["items" 0 "snippet" "title"]))

(defn get-duration [video-info]
  (get-in video-info ["items" 0 "contentDetails" "duration"]))

(defn fetch-video-info [id]
  (fetch-json (format info-url api-key id)))

;; ...
(defn parse-duration [d]
  (let [duration (.newDuration (javax.xml.datatype.DatatypeFactory/newInstance) d)
        [H M S] ((juxt #(.getHours %) #(.getMinutes %) #(.getSeconds %)) duration)]
    (str H "h" M "m" S "s")))

(defrecord Video [id title duration])

(def extract-video-parameters (juxt get-id get-title #(parse-duration (get-duration %))))

(defn find-video-id [s]
  (second (or (re-find #"http[s]?://www.youtube.com/watch\?v=([^&)\s]+)" s)
              (re-find #"http[s]?://youtu.be/([^&)\s]+)" s))))

(defn maybe-youtube-video [s]
  (some->> s
           find-video-id
           fetch-video-info
           extract-video-parameters
           (apply ->Video)))

(defn reply-with-video-info [irc msg {:keys [id title duration]}]
  (irc/reply irc msg (format "[%s] %s (https://youtu.be/%s)" duration title id)))

(defn handle-privmsg [irc {:keys [text] :as msg}]
  (prn text)
  (some->> (maybe-youtube-video text)
           (reply-with-video-info irc msg)))

(defn wrap-error-handler [f]
  (fn [& args] (try (apply f args) (catch Exception e (prn e)))))

(defonce connection
  (irc/connect "irc.freenode.net" 6667 "fet4oin"
               :callbacks {:privmsg (wrap-error-handler #'handle-privmsg)}))

(irc/join connection "#dolnykubin")
;; (irc/part connection "#dolnykubin")
