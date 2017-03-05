(ns cljbrickpi.brick
  (:require [cljbrickpi
             [sensor :as s]
             [motor :as m]
             [util :refer [get!]]])
  (:import
   [com.ergotech.brickpi
    BrickPi RemoteBrickPi BrickPiCommunications]))

;;optionally shove this in a single instance.
(defn ->brickpi [] (BrickPi/getBrickPi))
(defn ->remote-brickpi [ip]
  (doto (RemoteBrickPi.)
        (.setPiAddress (str ip))))

(defn set-sensor [pi port stype]
  (doto pi
    (.setSensor (s/->sensor stype) (get! s/ports port))))

;;             brickPi.setupSensors();
(defn setup-sensors [pi]
  (doto pi (.setupSensors)))

;;note: we could abstract the pi behind a simpler
;;interface, using a map-like deal to assoc onto
;;the named ports.
(defn sensor-value [pi port]
  (-> pi
      (.getSensor (get! s/ports port))
      (.getValue)))

;brickPi.setMotor(motor, MotorPort.MA)

(defn set-motor [pi port m]
  (doto pi
    (.setMotor m (get! m/motorports port))))

;;setup a motor on MA, a touch sensor on S1
;;rotate the motor until the touch sensor
;;is pressed.
;;we'll use a channel to handle this later.
;;For now, we'll just loop and sleep.

;;We need to use the updatelistener,
;;hook into it for a sequence of
;;update events.
(defn simple-test []
  (let [ma    (m/->motor)   ;;create motor
        touch (s/->sensor :ev3touch)
        touched? (fn [x]
                   (pos?
                    (s/sensor-value x))) 
                   
        bp    (-> (->brickpi) ;;get a handle      
                  (set-sensor :s1 touch)
                  (set-motor  :a ma)
                  (setup-sensors))
        ]
    (m/rotate ma 1 50)
              
  ))
