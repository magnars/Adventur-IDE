package no.advide

import javax.swing.JFileChooser
import groovy.swing.SwingBuilder

class Adventure {

  static def directoryPath

  static void choose() {
    /*
    JFileChooser fc = null
    new SwingBuilder().edt {
      fc = fileChooser(dialogTitle: "Velg en mappe med eventyr",
          id: "openDirectoryDialog", fileSelectionMode: JFileChooser.DIRECTORIES_ONLY) {}
    }
    if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) System.exit(0)
    directoryPath = fc.selectedFile.absolutePath
    */
    directoryPath = "/Users/magnars/projects/adventur/eventyr/master"
  }

  static boolean roomExists(int roomNumber) {
    new File(pathTo(roomNumber)).exists()
  }

  static String pathTo(int roomNumber) {
    "${directoryPath}/${subdir(roomNumber)}/A${roomNumber}.txt"
  }

  static String subdir(int roomNumber) {
    int hundreds = roomNumber / 100
    hundreds < 10 ? "A0${hundreds}" : "A${hundreds}"
  }
}
