;;idiomatic wrapper around BrickPiJava motor classes
(ns cljbrickpi.motor
  (:require [cljbrickpi.util :refer [get!]])
  (:import [com.ergotech.brickpi.motion Motor  MotorPort]
           [com.ergotech.brickpi BrickPiCommunications]))

;;helper macro for really long inner class name.
(defmacro Direction [x]
  `~(symbol (str "com.ergotech.brickpi.motion.Motor$Direction/" x)))

(def direction
  {:clockwise         (Direction CLOCKWISE) 
   :counter-clockwise (Direction COUNTER_CLOCKWISE)
   (Direction CLOCKWISE) :clockwise
   (Direction COUNTER_CLOCKWISE) :counter-clockwise})

(def motorports
  {:a MotorPort/MA
   :b MotorPort/MB
   :c MotorPort/MC
   :d MotorPort/MD
   })
   
(defn ^MotorPort motor-port [x]
  (get! motorports x))

;;should we create a custom type?
;;something that extends java.util.Map?

;;creates the motor, sets ticks/rever to 1440
(defn ->motor [] (Motor.))
(defn ^BrickPiCommunications get-brickpi [^Motor m] (.getBrickPi m))
;;we can dual-purpose this, to drop a motor we set the
;;instance to null.
(defn set-brickpi
  "Set the instance to which this Motor is associated.
   brickPi the current BrickPi instance to which this Motor is associated, or null 
   to remove the association with a controller."
  [^Motor m ^BrickPiCommunications p]
  (doto m (.setBrickPi p)))

                         
(defn reset-encoder
  "reset encoder reading"
  [^Motor m] (doto m (.resetEncoder)))

(defn ticks-per-revolution
  "The number of ticks per revolution of the motor. This is used to
   calculate the motor speed in RPM.
   @return The number of ticks per revolution of the motor"
  [^Motor m] (.getTicksPerRevolution m))

(defn set-ticks-per-revolution
 "Sets the number of ticks per revolution of the motor
  @param ticksPerRevolution The number of ticks per revolution of the motor"
  [^Motor m ticks]
  (doto m (.setTicksPerRevolution (int ticks))))

(defn current-encoder-value
  "returns the last raw encoder value read from the brick pi.
   @return the current raw encoder value."
  [^Motor m]
  (.getCurrentEncoderValue m))
    
(defn current-speed
  "The calculated current speed. This will return -1 if the speed is
   unknown. Counter clockwise speeds will be negative.
   @return the current calculated speed or -1 if the speed is unknown."
  [^Motor m]
  (.getCurrentSpeed m))

(defn last-reading-time [^Motor m]  (.getLastReadingTime m))

;;not sure we need this.
(defn encode-to-value-request
  "Encode the data associated with the motor to the outgoing message. The
  default method does nothing.
  
  @param message the BitSet representing the outgoing message.
  @param startLocation the starting bit location in the message at which to
  begin encoding
  @return the ending location. That is the startLocation for the next
  encoding."
  [^Motor m msg start-location]
  (.encodeToValueRequest m msg start-location))


(defn decode-values
  "Decode the encoder data associated with the motor from the incoming
   message. This will set the currentSpeed variable.
  
   @param wordLength the number of bits to read
   @param message the BitSet representing the outgoing message.
   @param startLocation the starting bit location in the message at which to
   begin decoding"
  [^Motor m word-length msg start-location]
  (doto m (.decodeValues word-length msg start-location)
  ))

     
(defn commanded-output
  "Returns the commanded output
  
   @return the commanded output +/-0-255 Negative indicates Counter
   Clockwise (however you choose to define that)."
  [^Motor m]
  (.getCommandedOutput m))
    

(defn set-commanded-output
  "Set the commanded output. For convenience the speed is set as an int
   although the max is still 255.
  
   @param commandedOutput +/- 0-255 Negative values indicate Counter
   Clockwise (however you choose to define that)."
  [^Motor m co]
  (doto m (.setCommandedOutput (int co))))

(defn direction [^Motor m] (get! direction (.getDirection m)))
(defn set-direction [^Motor m dir]
  (doto m (.setDirection
           ^com.ergotech.brickpi.motion.Motor$Direction
           (get! direction dir))))

(defn enabled?
  "Returns the state of the enabled flag.
   
   @return true if the motor is enabled."
  [^Motor m]
  (.isEnabled m))

(defn set-enabled   
  "Enables or disables the motor
     
   @param enabled set to true to enable the motor."
  [^Motor m v]
  (doto m (.setEnabled ^Boolean (if v true false))))

(defn rotate
  "Rotate the motor a certain number of rotations and then stop. Fractional
   rotation (eg 0.25) are acceptable. Negative values imply
   counter-clockwise rotation.
  
   @param rotations the number of rotations to complete.
   @param commandedOutput the speed at which to perform the rotation."
  [^Motor m rotations commanded-output]
  (doto m (.rotate (double rotations) (int commanded-output))))

