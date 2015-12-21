/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Relaxed Complexity, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 *******************************************************************************/

package com.relaxedcomplexity.sounder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Maintains the state of the sound system and contains methods that allow
 * the sound to be manipulated by starting and stopping it, as well as changing
 * volume and frequency.
 * <p>
 * SoundPlayer runs on the main thread and facilitates communication between
 * the mouse event handler and the sound system (MouseCntl and SineSynth).
 * 
 * @author Jim Medlock
 *
 */
public class SoundPlayer {
  
  private static final Logger logger = Logger.getLogger("com.relaxedcomplexity.sounder");
  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  private double       frequency = 0d;
  private double       startingFrequency = 400;
  private double       frequencyDelta = 20;
  
  private float        gain = 0f;
  private float        gainDelta = 0;
  
  private boolean      soundPlaying = false;
  
  // -------------------------------------------------------------------------
  // Sound Manipulation Methods
  // -------------------------------------------------------------------------

  /**
   * Modify volume/frequency of the sound
   * 
   * @param direction Direction enum value
   */
  public void modifySound(MouseCntl.Direction direction) {
    // TODO: Finish coding modifySound method
    switch (direction) {
      case LEFT:
        break;
      case RIGHT:
        break;
      case UP:
        incrGain();
        break;
      case DOWN:
        decrGain();
        break;
      default:
        logger.severe("Invalid direction passed to modifySound. direction=" + direction);
    }
  }
  
  // -------------------------------------------------------------------------
  // Frequency Methods
  // -------------------------------------------------------------------------

  /**
   * Decrement the frequency 
   */
  public void decrFrequency() {
    frequency -= frequencyDelta;
  }
  /**
   * Get current frequency value
   */
  public double getFrequency() {
    return frequency;
  }
  
  /**
   * Increment the frequency 
   */
  public void incrFrequency() {
    frequency += frequencyDelta;
  }
  
  /**
   * Set  current frequency value
   * 
   * @param newFrequency New frequency value
   * 
   * TODO: Perform editing on inbound newFrequency value
   */
  public void setFrequency(double newFrequency) {
    this.frequency = newFrequency;
  }
 
  // -------------------------------------------------------------------------
  // Gain Methods (volume)
  // -------------------------------------------------------------------------

  /**
   * Decrement the gain (volume) level
   */
  public void decrGain() {
    gain -= gainDelta;
  }
  
 /**
   * Get current gain (volume) level
   */
  public float getGain() {
    return gain;
  }
  
  /**
   * Increment the gain (volume) level
   */
  public void incrGain() {
    gain += gainDelta;
  }
  
  /**
   * Set current gain (volume) level
   * 
   * @param newGain New volume level
   * 
   * TODO: Perform editing on inbound newGain value
   */
  public void setGain(float newGain) {
    this.gain = newGain;
  }
 
  // -------------------------------------------------------------------------
  // Sound Playing State Methods
  // -------------------------------------------------------------------------

  /**
   * Get sound state (i.e. current playing or not playing)
   */
  public boolean isSoundPlaying() {
    return soundPlaying;
  }
  
  /**
   * Toggle sound on/off.
   * 
   * Since playing sound is an operation that's concurrent with event 
   * processing a separate thread must be created for it. 
   * 
   */
  public void toggleSound() {
    if (soundPlaying) {
      soundPlaying = false;
    } else {
      soundPlaying = true;
      executor.submit(() -> {
        SineSynth.playAudio(this);
      });
     }
  }

}
