import javax.sound.midi.*;
import java.io.File;

public class javaSimpleSynth {
    public static void main(String[] args) {
        try {
            // Open the custom soundfont synthesizer
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();

            // Disable the default soundbank
            synth.unloadAllInstruments(synth.getDefaultSoundbank());

            // Load the custom soundbank
            Soundbank soundbank = MidiSystem.getSoundbank(new File("path_to_soundfont.sf2"));
            synth.loadAllInstruments(soundbank);

            // Create a sequencer with a disconnected sequence
            Sequencer sequencer = MidiSystem.getSequencer(false);
            sequencer.open();

            // Connect the sequencer to the custom synthesizer
            Transmitter transmitter = sequencer.getTransmitter();
            Receiver receiver = synth.getReceiver();
            transmitter.setReceiver(receiver);

            // Load and play the MIDI sequence
            Sequence sequence = MidiSystem.getSequence(new File("path_to_midi_file.mid"));
            sequencer.setSequence(sequence);
            sequencer.start();

            // Wait for the sequence to finish
            while (sequencer.isRunning()) {
                Thread.sleep(100);
            }

            // Stop and close the sequencer and synthesizer
            sequencer.stop();
            sequencer.close();
            synth.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}