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
 * This GeoBBox represents an area rectangle limited only in south latitude.
 *
 * @lucene.internal
 */
public class GeoNorthLatitudeZone extends GeoBBoxBase {
  public final double bottomLat;
  public final double cosBottomLat;
  public final SidedPlane bottomPlane;
  public final GeoPoint interiorPoint;
  public final static GeoPoint[] planePoints = new GeoPoint[0];

  public final GeoPoint bottomBoundaryPoint;

  // Edge points
  public final GeoPoint[] edgePoints;

  public GeoNorthLatitudeZone(final double bottomLat) {
    this.bottomLat = bottomLat;

    final double sinBottomLat = Math.sin(bottomLat);
    this.cosBottomLat = Math.cos(bottomLat);

    // Construct sample points, so we get our sidedness right
    final Vector bottomPoint = new Vector(0.0, 0.0, sinBottomLat);

    // Compute an interior point.  Pick one whose lat is between top and bottom.
    final double middleLat = (Math.PI * 0.5 + bottomLat) * 0.5;
    final double sinMiddleLat = Math.sin(middleLat);
    this.interiorPoint = new GeoPoint(Math.sqrt(1.0 - sinMiddleLat * sinMiddleLat), 0.0, sinMiddleLat);
    this.bottomBoundaryPoint = new GeoPoint(Math.sqrt(1.0 - sinBottomLat * sinBottomLat), 0.0, sinBottomLat);

    this.bottomPlane = new SidedPlane(interiorPoint, sinBottomLat);

    this.edgePoints = new GeoPoint[]{bottomBoundaryPoint};
  }

  @Override
  public GeoBBox expand(final double angle) {
    final double newTopLat = Math.PI * 0.5;
    final double newBottomLat = bottomLat - angle;
    return GeoBBoxFactory.makeGeoBBox(newTopLat, newBottomLat, -Math.PI, Math.PI);
  }

  @Override
  public boolean isWithin(final Vector point) {
    return
        bottomPlane.isWithin(point);
  }

  @Override
  public boolean isWithin(final double x, final double y, final double z) {
    return
        bottomPlane.isWithin(x, y, z);
  }

  @Override
  public double getRadius() {
    // This is a bit tricky.  I guess we should interpret this as meaning the angle of a circle that
    // would contain all the bounding box points, when starting in the "center".
    if (bottomLat < 0.0)
      return Math.PI;
    double maxCosLat = cosBottomLat;
    return maxCosLat * Math.PI;
  }

  /**
   * Returns the center of a circle into which the area will be inscribed.
   *
   * @return the center.
   */
  @Override
  public GeoPoint getCenter() {
    return interiorPoint;
  }

  @Override
  public GeoPoint[] getEdgePoints() {
    return edgePoints;
  }

  @Override
  public boolean intersects(final Plane p, final GeoPoint[] notablePoints, final Membership... bounds) {
    return
        p.intersects(bottomPlane, notablePoints, planePoints, bounds);
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
    bounds.noLongitudeBound().noTopLatitudeBound().addLatitudeZone(bottomLat);
    return bounds;
  }

  @Override
  public int getRelationship(final GeoShape path) {
    final int insideRectangle = isShapeInsideBBox(path);
    if (insideRectangle == SOME_INSIDE)
      return OVERLAPS;

    final boolean insideShape = path.isWithin(bottomBoundaryPoint);

    if (insideRectangle == ALL_INSIDE && insideShape)
      return OVERLAPS;

    // Second, the shortcut of seeing whether endpoints are in/out is not going to
    // work with no area endpoints.  So we rely entirely on intersections.

    if (
        path.intersects(bottomPlane, planePoints))
      return OVERLAPS;

    // There is another case for latitude zones only.  This is when the boundaries of the shape all fit
    // within the zone, but the shape includes areas outside the zone crossing a pole.
    // In this case, the above "overlaps" check is insufficient.  We also need to check a point on either boundary
    // whether it is within the shape.  If both such points are within, then CONTAINS is the right answer.  If
    // one such point is within, then OVERLAPS is the right answer.

    if (insideShape)
      return CONTAINS;

    if (insideRectangle == ALL_INSIDE)
      return WITHIN;

    return DISJOINT;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof GeoNorthLatitudeZone))
      return false;
    GeoNorthLatitudeZone other = (GeoNorthLatitudeZone) o;
    return other.bottomPlane.equals(bottomPlane);
  }

  @Override
  public int hashCode() {
    int result = bottomPlane.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "GeoNorthLatitudeZone: {bottomlat=" + bottomLat + "(" + bottomLat * 180.0 / Math.PI + ")}";
  }
}

