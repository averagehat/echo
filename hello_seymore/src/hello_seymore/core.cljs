(ns hello-seymore.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http] 
            [goog.string :as gstring]
            [cljs.core.async :refer [<!]]
            [reagent.core :as r] 
            [sablono.core :as sab]
            [clojure.string :as str])) 
;;state
(def query (r/atom {}))
(def response (r/atom ())) 
(def user-time (r/atom 12))
(def title "text")

(def es-url  "http://localhost:9200/subtitles/_search?q=") 
(def start-hi "<span style=\"background: yellow;\">") 
(def end-hi "</span>") 

(def conn (js/elasticsearch.Client {:host "localhost:9200" :log "trace"})) 

(defn basic-search [raw-q]
  (let [qmap (into (sorted-map) (filter second raw-q))] ;drop nil values
    (.search conn (clj->js
      {:index "subtitles"
       :type "srt"
       :body
          (assoc qmap
            :highlight {
              :fields {:gb_format {:number_of_fragments 0 }}
              :pre_tags [start-hi]
              :post_tags [end-hi]})})
      #(reset! response (-> %2 (js->clj :keywordize-keys true) :hits :hits )))))


;(defn query [q] ;; had CORS problems without with-credentials? set to false
;  (go (let [res(<! (http/get (str es-url q) {:with-credentials? false}))]
;    (reset! response 
;      (->> (get-in [:body :hits :hits] res) 
;           (map #(get-in % [:_source :text]))
;           (str/join "\n"))))))

(defn allow-html
  "allows highlighting to show up. Will allow dangerous html tags."
  ([comp content]
     (allow-html comp nil content))
  ([comp props content]
     [comp (assoc props :dangerouslySetInnerHTML {:__html content})])) 

(defn list-component "for displaying the (highlighted) query response." [res]
  [:div   
   (doall
   (for [r res] 
     (let [k (:_id r) e (:_source r)]
       ^{:key k} [:div>p (:text e)
       [:button
         {:on-click  
          (fn [_] 
        (let [vid (.getElementById js/document "vid")]
            (do
              ;TypeError: s.substring is not a function-> obj. but not a string
               (reset! user-time (:counter e ))
            (set!  (.-currentTime vid) @user-time))))}
               (:counter e)]
               (let [ highlited (comp :text :highlight)] 
               (allow-html :pre (highlited r)))])))])
(defn vid-component []
  [:div
  [:audio {:controls true :autoPlay false :id "vid" } ; these are listed without the booleans in pure html
   ;{:width 320 :height 176 :id "vid"}
     ;switch to type "video/wav" and this will work for video
   [:source {:src "vid.wav" :type "audio/wav"} "no html5 video!"]]])

(defn control-component []
  [:div>button>input
           {:auto-complete "off"
            :on-change  (fn [i]
                          (let [vid (.getElementById js/document "vid")
                                new-time (-> i .-target .-value int)] 
                          (set! (.-currentTime vid) new-time))) 
            :type "text"} ])
                          ;(reset! user-time new-time ) ;; this doesn't work!
 ;@user-time note that's  created once and not changed! hmm.  
            ;:value @user-time
            ;:on-change  #((reset! user-time (-> % .-target .-value) )

(def get-value #(-> % .-target .-value))
;(defn q-input [query]
;        (let [ q (:query (:term query))
;               path [:query :term]]
;         [:p  (str q \: @user-time)
;         [:input  
;             {:value q 
;              :type "text"
;
;              ;:on-change (fn [x] (swap! query update-in path #(get-value x)) )} ]]) )
;
;              :on-change  (fn [x] (swap! user-query assoc-in [:query :term] (-> x get-value))) }]]) )
;;(swap
;;(swap! user-query assoc-in [:query :term] "foo")
;(swap! user-query update-in [:query :term] (fn [] "SDFAF"))


;; why doesn't this work?
;;hiccup is in order of: [tag attributes contents]
;(defn like-component []
;  ;[:div
;   [:pre  @response
;       [:h3 "Elastic Search" ;(str "Elastic-Search" (:response @app-state))
;        ^{:key "query-input"}  [q-input @user-query]
;         [:button {:type "button" 
;                   :on-click #(basic-search @user-query)}]
;    [[:pre {} @response]  {} vid-component ] {} [control-component]]] ) 
;
(defn gen-input 
  "given a field (f), it's current value (v), and path in the query map (path),
  render a input text box which will update the query map appropriately."
  [f v path]
  [:p (str (name f) "=" v)
    [:input {:type "text" :value v
              :on-change (fn [x] (swap! query update-in path #(get-value x)) )} ]]) 
(def fields [:text])
(defn main-component []
  [:div
    [:h3 "Search"] [:br]
   (doall 
       (for [f fields] 
         (let [path [:query :term f]]
         ; ^{:key-> each element gets a unique id
         ^{:key (str "id_" f)} [gen-input f (get-in @query path) path])))
   [:button {:type "button" :name "query" :width "900" :height "9999"
             :on-click #(basic-search @query)} "Search"]
    [[:pre {} @response]  {} vid-component ] {} [control-component]  
      [list-component @response]])

(r/render [main-component]
      (js/document.getElementById "app")) 
