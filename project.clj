(defproject joinr/cljbrickpi "0.1.0-SNAPSHOT"
  :description "A wrapper around BrickPiJava, by ErgoTech Systems. 
                BrickPiJava is distributed under the 
                Creative Commons Attribution-ShareAlike 3.0  license. 
                (http://creativecommons.org/licenses/by-sa/3.0/) 
                from https://github.com/DexterInd/BrickPiJava"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-localrepo "0.5.3"]
            [brightnorth/uberjar-deploy "1.0.1"]]
  :dependencies [[org.clojure/clojure "1.8.0" :scope "provided"]
                 [org.slf4j/slf4j-api "1.7.12"]
                 [com.ergotech/brickpi "1.0-SNAPSHOT"]]
  :aliases {"jar"    "uberjar"
            "deploy" "uberjar-deploy"}
  )
