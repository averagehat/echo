(ns hello-seymore.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http] 
            [goog.string :as gstring]
            [cljs.core.async :refer [<!]]
            [reagent.core :as r] 
            [sablono.core :as sab]
            [clojure.string :as str])) 
;;state
(def app-state (r/atom { :likes 200 :response "unclicked" :query "FJ966086.1"})) 
(def user-query (r/atom "*:*")) 
(def response (r/atom "none yet"))
(def user-time (r/atom 12))


(def es-url  "http://localhost:9200/sequence/_search?q=") 

(defn query [q] ;; had CORS problems without with-credentials? set to false
  (go (let [res(<! (http/get (str es-url q) {:with-credentials? false}))]
    (reset! response 
      (-> res
        (get-in [:body :hits :hits]) 
        (->> (map #(get-in % [:_source :gb_format])))))))) 

(defn vid-component []
  [:div
  [:audio {:controls true :autoPlay false :id "vid" } ; these are listed without the booleans in pure html
   ;{:width 320 :height 176 :id "vid"}
     ;switch to type "video/wav" and this will work for video
   [:source {:src "vid.wav" :type "audio/wav"} "no html5 video!"]]])

(defn control-component []
  [:div 
  [:button 
         [:input  ;;text box for query
           {:auto-complete "off"
            ;:value @user-time
            ;:on-change  #((reset! user-time (-> % .-target .-value) )
            :on-change  (fn [i]
                          (let [vid (.getElementById js/document "vid")
                                new-time (-> i .-target .-value int)] 
                          (set! (.-currentTime vid) new-time))) 
                          ;(reset! user-time new-time ) ;; this doesn't work!
            :type "text"} ;@user-time note that's  created once and not changed! hmm.
          
          ]]])

;;hiccup is in order of: [tag attributes contents]
(defn like-component []
  [:div
   
    [:h1 "rather, a change: " (:likes @app-state)]
       [:div [:a {:href "#"
                  :on-click #(swap! app-state update-in [:likes] inc)}
                 "Thumbs up"]]
       [:h3 "Elastic Search" ;(str "Elastic-Search" (:response @app-state))
         [:button {:type "button" :name "query"
                   :on-click #(query @user-query)}]
         [:br]
         ;[:p  @user-query] 
         [:p  (str @user-query \: @user-time)] 
         [:input  ;;text box for query
           {:auto-complete "off"
            :class "autocomplete"
            :value @user-query
            :on-change  #(reset! user-query (-> % .-target .-value))
            :type "text"}]]
   
    ;[[:pre @response] {} [vid-component {} control-component] ]])
    [[:pre @response]  {} vid-component ] {} [control-component]] )




(r/render [like-component]
          (js/document.getElementById "app"))
