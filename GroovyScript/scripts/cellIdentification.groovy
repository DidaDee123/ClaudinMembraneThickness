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

//length 36 = 8.2707um
//length 1 = 0.22974166666666667


import qupath.lib.gui.measure.ObservableMeasurementTableData
import qupath.lib.roi.RoiTools 



private def convertToObjects() {
    def detections = getDetectionObjects()
    def newAnnotations = detections.collect {
        return PathObjects.createAnnotationObject(it.getROI(), it.getPathClass())
    }
    removeObjects(detections, true)
    insertObjects(newAnnotations)
}

private def smoothen() {
    rings = getAnnotationObjects()
    selectAnnotations()
    runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons": 15.0,  "lineCap": "Round",  "removeInterior": false,  "constrainToParent": false}');
    removeObjects(rings, true)
    selectAnnotations()
    rings = getAnnotationObjects()
    runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons": -15.0,  "lineCap": "Round",  "removeInterior": false,  "constrainToParent": false}');
    removeObjects(rings,true)
    resetSelection()

}


/**
 * Rename all cells from each annotation hierarchy for better ID
 * Print out defined measurements of each cell
 */
private def identifyCells() {
   // Get children annotations of hierarchy
    def children = []
    int annoIndex = 0
    getAnnotationObjects().each {
        if (it.getChildObjects() != null) {
            children[annoIndex] = it.getChildObjects()
            annoIndex++
        }
    }
    
    // Rename children annotations for better ID
    for (int j = 0; j < children.size(); j++) {
        println "${getAnnotationObjects()[j].getName()}:"
        
        int cell_index = 1
        for (int i = 0; i < children[j].size(); i++) { 
            new_child = 'cell_' + cell_index
            children[j][i].setName(new_child)
            println "${children[j][i].getName()}:"
            println "${children[j][i].getROI().getGeometry()}"
            cell_index++
        }
        println ""
    } 
}

/**
 * Same concept as identifyCells(). However must be executed after hierarchy is set
 */
private def nameDetections() {
    getAnnotationObjects().each {
        parentName = it.getName()
        
        if (it.hasChildObjects()) {
            childIndex = 1
            it.getChildObjects().each {
                it.setName(parentName + "_" + "cell_" + childIndex)
                childIndex++
            }
        }
    }    
}


/**
 * Check for adjacent cells of selected cell
 * Adjacent cells in list
 */
private def findAdjacents(String roi) {
    int averageDiameter = getAverageDiameter(roi)
    println "Average Diameter: " + averageDiameter
    int childSize = 0
    for (annotation in getAnnotationObjects()) {
        if (annotation.getName() == roi) {
            childSize = annotation.getChildObjects().size()
            break
        }
    }
}


/**
 * 
 */
private int getAverageDiameter(String roi) {
    int averageDiameter = 0
    for (annotation in getAnnotationObjects()) {
        if (annotation.hasChildObjects() && annotation.getName() == roi) {
            annotation.getChildObjects().each {
                print(it)
                print(it.getROI().getLength())
                averageDiameter = averageDiameter + it.getROI().getLength()
            }
            return averageDiameter / 3.14 / annotation.getChildObjects().size()
            
        }
    }
}

/**
 * Inserts 5 Line-annotations between random cell membranes
 * Prints out the measurements of thickness of each annotation
 */
private def insertThicknessMeasurement() {
//    int z = 0
//    int t = 0
//    def plane = ImagePlane.getPlane(z, t)
//    def roi = ROIs.createLineROI(21450.83, 14484.25, 21449.41, 14483.4, plane)
//    //def roi = ROIs.createLineROI(21445.529, 14490.29, 0, 0, plane)
//    def annotation = PathObjects.createAnnotationObject(roi)
//    annotation.setName("Thickness_1")
//    color = getColorRGB(0, 100, 100)
//    annotation.setColor(color)
//    addObject(annotation)
//    
//    def ob = new ObservableMeasurementTableData();
//    def annotations = getAnnotationObjects()
//    ob.setImageData(getCurrentImageData(),  annotations);
//    int index = 0
//    annotations.each { 
//        length=ob.getNumericValue(it, "Length µm")
//        print "Thickness_${index}: ${length}µm"
//        index++
//    }
    def tools = new RoiTools()
    def annotations = getAnnotationObjects()
    annotations.each {
        print(it)
        print("Distance between cells:")
        println tools.getBoundaryDistance(it.getChildObjects()[0].getROI(),
                                            it.getChildObjects()[1].getROI())
    }
}    


/**
 * Main
 */
public static void main(String[] args) {
    nameDetections()
    
}