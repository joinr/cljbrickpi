(ns cljbrickpi.sensor
  (:require [cljbrickpi.util :refer [get!]])
  (:import [com.ergotech.brickpi.sensors
            Sensor SensorPort SensorType TouchSensor
            EV3TouchSensor] 
           [com.ergotech.brickpi BrickPiCommunications]))


  
(def sensors
  {:ultrasonic SensorType/Ultrasonic
   :ultrasonicss  SensorType/UltrasonicSS
   :raw SensorType/Raw
   :touch :touch
   :ev3touch :ev3touch}
  )

(def ports
  {:s1 SensorPort/S1
   :s2 SensorPort/S2
   :s3 SensorPort/S3
   :s4 SensorPort/S4
   })

(defn ^SensorPort sensor-port [x]
  (get! ports x))


(defn sensor? [x]
  (isa? (class x)
        com.ergotech.brickpi.sensors.Sensor))

(defn ->sensor [stype]
  (if (sensor? stype) stype
      (let [res (get sensors stype)]
        (case res
          :touch (TouchSensor.)
          :ev3touch (EV3TouchSensor.)
          (Sensor. res)))))

(defn sensor-value [^Sensor s]
  (.getValue s))

;;adding EV3 IR sensor types.
