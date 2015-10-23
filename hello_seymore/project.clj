(defproject hello-seymore "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [sablono "0.3.6"]
                 [jayq "2.5.4"]
                 [cljs-http "0.1.37"]
                 [org.clojure/clojurescript "1.7.122"]
                 [reagent "0.5.1"]]
  :plugins [[lein-figwheel "0.4.0"]]
  :clean-targets [:target-path "out"]
  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]
              :figwheel true
              :compiler {:main "hello-seymore.core"} 
             }]
   })


;                                    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
;  :cljsbuild {
;    :builds [{:id "dev"
;              :source-paths ["src"]
;              ;:figwheel true
;              :figwheel {
;                          :nrepl-port 7888
;                         }
;
;              :compiler {:main "hello-seymore.core"} 
;             }]
;   })
