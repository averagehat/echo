(ns figwheel.connect (:require [hello-seymore.core] [figwheel.client] [figwheel.client.utils]))
(figwheel.client/start {:nrepl-port 7888, :build-id "dev", :websocket-url "ws://localhost:3449/figwheel-ws"})

