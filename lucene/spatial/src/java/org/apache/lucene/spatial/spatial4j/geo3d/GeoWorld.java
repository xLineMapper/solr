package org.apache.lucene.spatial.spatial4j.geo3d;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Bounding box including the entire world.
 *
 * @lucene.internal
 */
public class GeoWorld extends GeoBBoxBase {
  protected final static GeoPoint originPoint = new GeoPoint(1.0, 0.0, 0.0);
  protected final static GeoPoint[] edgePoints = new GeoPoint[0];

  public GeoWorld() {
  }

  @Override
  public GeoBBox expand(final double angle) {
    return this;
  }

  @Override
  public double getRadius() {
    return Math.PI;
  }

  /**
   * Returns the center of a circle into which the area will be inscribed.
   *
   * @return the center.
   */
  @Override
  public GeoPoint getCenter() {
    // Totally arbitrary
    return originPoint;
  }

  @Override
  public boolean isWithin(final Vector point) {
    return true;
  }

  @Override
  public boolean isWithin(final double x, final double y, final double z) {
    return true;
  }

  @Override
  public GeoPoint[] getEdgePoints() {
    return edgePoints;
  }

  @Override
  public boolean intersects(final Plane p, final GeoPoint[] notablePoints, final Membership... bounds) {
    return false;
  }

  /**
   * Compute longitude/latitude bounds for the shape.
   *
   * @param bounds is the optional input bounds object.  If this is null,
   *               a bounds object will be created.  Otherwise, the input object will be modified.
   * @return a Bounds object describing the shape's bounds.  If the bounds cannot
   * be computed, then return a Bounds object with noLongitudeBound,
   * noTopLatitudeBound, and noBottomLatitudeBound.
   */
  @Override
  public Bounds getBounds(Bounds bounds) {
    if (bounds == null)
      bounds = new Bounds();
    bounds.noLongitudeBound().noTopLatitudeBound().noBottomLatitudeBound();
    return bounds;
  }

  @Override
  public int getRelationship(final GeoShape path) {
    if (path.getEdgePoints().length > 0)
      // Path is always within the world
      return WITHIN;

    return OVERLAPS;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof GeoWorld))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "GeoWorld";
  }
}
