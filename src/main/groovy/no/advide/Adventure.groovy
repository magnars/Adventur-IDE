package no.advide

class Adventure {

  static Adventure current

  static void choose() {
    /*
    JFileChooser fc = null
    new SwingBuilder().edt {
      fc = fileChooser(dialogTitle: "Velg en mappe med eventyr",
          id: "openDirectoryDialog", fileSelectionMode: JFileChooser.DIRECTORIES_ONLY) {}
    }
    if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) System.exit(0)
    current = new Adventure(fc.selectedFile)
    */
    current = new Adventure(new File("/Users/magnars/projects/adventur/eventyr/master"))
  }

  def directoryPath

  Adventure(File directory) {
    directoryPath = directory.absolutePath
  }

  boolean roomExists(int roomNumber) {
    roomFile(roomNumber).exists()
  }

  private File roomFile(int roomNumber) {
    return new File(pathTo(roomNumber))
  }

  String pathTo(int roomNumber) {
    "${directoryPath}/${subdir(roomNumber)}/A${roomNumber}.txt"
  }

  String subdir(int roomNumber) {
    int hundreds = roomNumber / 100
    hundreds < 10 ? "A0${hundreds}" : "A${hundreds}"
  }

  Page loadRoom(int roomNumber) {
    new Page("Rom ${roomNumber}", roomFile(roomNumber))
  }

  Page loadNotes() {
    new Page('Notatblokk', new File("${directoryPath}/notat.txt"))
  }
}
