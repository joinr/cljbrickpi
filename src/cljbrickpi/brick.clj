(ns cljbrickpi.brick
  (:require [cljbrickpi [sensor :as s] [motor :as m]])
  (:import
   [com.ergotech.brickpi
    BrickPi RemoteBrickPi BrickPiCommunications]))

;;optionally shove this in a single instance.
(defn ->brickpi [] (BrickPi/getBrickPi))
(defn ->remote-brickpi [ip]
  (doto (RemoteBrickPi.)
        (.setPiAddress (str ip))))

(defn set-sensor [pi stype port]
  (doto pi
    (.setSensor (->sensor stype) (get! ports port))))

;;             brickPi.setupSensors();
(defn setup-sensors [pi]
  (doto pi (.setupSensors)))

;;note: we could abstract the pi behind a simpler
;;interface, using a map-like deal to assoc onto
;;the named ports.
(defn sensor-value [pi port]
  (-> pi
      (.getSensor (get! ports ports))
      (.getValue)))
