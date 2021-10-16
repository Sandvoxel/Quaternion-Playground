package processing.sketches.physics.math;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Mesh {

    Vertex[] vertices;

    public Mesh() {}

    public Mesh(Vertex... vertices) {
        this.vertices = vertices;
    }


    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(PVector... vertices) {
        this.vertices = new Vertex[vertices.length];

        ArrayList<Vertex> vertexArrayList = new ArrayList<>();
        Arrays.stream(vertices).forEach(vector -> vertexArrayList.add(new Vertex(vector)));

        vertexArrayList.toArray(this.vertices);
    }


    public Stream<Vertex> getVertexStream(){
        return Arrays.stream(vertices);
    }

}
