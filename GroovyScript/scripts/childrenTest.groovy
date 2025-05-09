/*
 * getAnnotationObjects() -> class java.util.ArrayList
 * getAnnotationObjects()[] -> class PathAnnotationObject
 * it.getChildObjects() -> class java.util.Collections$UnmodifiableCollection
 * it.getChildObjects()[] -> class PathCellObject
 * 
 * https://qupath.github.io/javadoc/docs/qupath/lib/objects/PathAnnotationObject.html
 * https://qupath.github.io/javadoc/docs/qupath/lib/objects/PathCellObject.html
 * https://qupath.github.io/javadoc/docs/qupath/lib/roi/PolygonROI.html
 * 
 * 
 */


// Get children annotations of hierarchy

//print(getAnnotationObjects().size())
//def children = []
//print(children.size())
//int annoIndex = 0
//getAnnotationObjects().each {
//    children[annoIndex] = it.getChildObjects()
//    annoIndex++
//}
//print(children.size())
//// Rename children annotations for better ID
//for (int j = 0; j < children.size(); j++) {
//    
//    int cell_index = 1
//    for (int i = 0; i < children[j].size(); i++) { 
//        new_child = 'cell_' + cell_index
//        children[j][i].setName(new_child)
//        print(children[j][i].getName())
//        println "${children[j][i].getROI().getGeometry()}}"
//        cell_index++
//    }
//}
//

def roiName = "roi1"
getAnnotationObjects().each {
    
    if (it.hasChildObjects() && it.getName() == roiName) {
        averageDiameter = 0
        it.getChildObjects().each {
            print(it)
            print(it.getROI().getLength())
            averageDiameter = averageDiameter + it.getROI().getLength()
        }
        print(averageDiameter / 3.14 / it.getChildObjects().size())
    }
}



