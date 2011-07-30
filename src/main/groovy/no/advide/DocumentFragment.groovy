package no.advide

class DocumentFragment {

  Document document
  int startIndex
  int length

  List<String> getLines() {
    document.lines[ startIndex..(startIndex + length - 1) ]
  }

}
