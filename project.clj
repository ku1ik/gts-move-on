(defproject gts-move-on "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-core "1.3.2"]
                 [compojure "1.3.4"]
                 [enlive "1.1.5"]
                 [org.clojure/data.json "0.2.6"]]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler gts-move-on.core/handler})
