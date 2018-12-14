package org.bimserver.serializers.binarygeometry;

import java.util.ArrayList;
import java.util.List;

public class GeometryBuffer {
	private static final int THRESHOLD = 100000;

	private final List<GeometrySubBuffer> geometryMappings = new ArrayList<>();
	private int currentIndex;

	private boolean initSent;

	private int nrIndices;

	private int nrVertices;

	private int nrColors;

	private int preparedByteSize;

	private int nrObjects;

	public GeometryBuffer() {
		GeometrySubBuffer current = new GeometrySubBuffer(this, 0);
		geometryMappings.add(current);
	}
	
	public List<GeometrySubBuffer> getGeometryMappings() {
		return geometryMappings;
	}

	public boolean isEmpty() {
		return geometryMappings.isEmpty();
	}

	public GeometrySubBuffer getCurrentGeometryMapping(boolean canUpdate) {
		GeometrySubBuffer current = geometryMappings.get(geometryMappings.size() - 1);
		if (current.getNrTriangles() > THRESHOLD && canUpdate) {
			current = new GeometrySubBuffer(this, current.getBaseIndex() + current.getNrVertices() / 3);
			geometryMappings.add(current);
		}
		return current;
	}

	public boolean hasNextGeometryMapping() {
		return currentIndex < geometryMappings.size();
	}
	
	public GeometrySubBuffer getNextGeometryMapping() {
		GeometrySubBuffer geometrySubBuffer = geometryMappings.get(currentIndex);
		currentIndex++;
		return geometrySubBuffer;
	}

	public void setInitSent() {
		this.initSent = true;
	}
	
	public boolean initSent() {
		return initSent;
	}

	public int getPreparedByteSize() {
		return preparedByteSize;
	}

	public int getNrObjects() {
		return nrObjects;
	}

	public int getNrIndices() {
		return nrIndices;
	}

	public int getNrVertices() {
		return nrVertices;
	}

	public int getNrColors() {
		return nrColors;
	}

	public void incNrIndices(int nrIndices) {
		this.nrIndices += nrIndices;
	}

	public void incNrVertices(int nrVertices) {
		this.nrVertices += nrVertices;
	}

	public void incNrColors(int nrColors) {
		this.nrColors += nrColors;
	}

	public void incPreparedByteSize(int byteSize) {
		this.preparedByteSize += byteSize;
	}

	public void incNrObjects() {
		this.nrObjects++;
	}
}