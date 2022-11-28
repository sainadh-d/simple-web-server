(defproject simple-server "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [http-kit "2.7.0-alpha1"]
                 [ring-cors "0.1.13"]
                 [ring/ring-defaults "0.3.4"]
                 [org.clojure/data.json "2.4.0"]]
  :main ^:skip-aot simple-web-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:dependencies [[nrepl "1.1.0-alpha1"]]}})
