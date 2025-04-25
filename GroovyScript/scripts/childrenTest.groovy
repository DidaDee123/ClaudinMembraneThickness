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
def children = null
getAnnotationObjects().each {
    children = it.getChildObjects()
}

// Rename children annotations for better ID
int cell_index = 1
for (int i; i < children.size(); i++) { 
    new_child = 'cell_' + cell_index
    children[i].setName(new_child)
    print(children[i].getName())
    println "${children[i].getROI().getGeometry()}}"
    cell_index++
}

