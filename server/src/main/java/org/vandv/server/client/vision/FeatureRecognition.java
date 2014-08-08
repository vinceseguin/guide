package org.vandv.server.client.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Determine if two Matrices correspond to the same scene using their feature points.
 * Part of the chain of responsibility (GOF)
 * Based on this tutorial:
 * http://docs.opencv.org/doc/tutorials/features2d/feature_flann_matcher/feature_flann_matcher.html
 *
 * Created by vinceseguin on 16/07/14.
 */
public class FeatureRecognition extends ImageRecognition {

    private static final double ABSOLUTE_MINIMAL_DISTANCE = 0.02;
    private static final double DISTANCE_MULTIPLIER = 2;

    /**
     * Constructor
     * @param features1 Matrix representing the feature points of the first image.
     * @param features2 Matrix representing the feature points of the second image.
     */
    public FeatureRecognition(Mat features1, Mat features2) {
        super(features1, features2);
    }

    /**
     * Determines if the two matrices are of the same scene.
     * If the logic in this method returns that they are, the
     * responsibility is passed down the chain.
     * The matrices are feature points in this class.
     * @return <code>true</code> if the two matrices are of
     * the same scene
     * <code>false</code> otherwise
     */
    @Override
    public boolean handleRequest() {

        MatOfDMatch matches = matchDescriptors(obj1, obj2);

        List<DMatch> goodMatches = calculateGoodMatches(matches, calculateMinDistanceBetweenMatch(matches));

        return goodMatches.size() / matches.rows() >= 0.5 && successor.handleRequest();
    }

    /**
     * Match the descriptors of the two matrices of features points.
     * For now the algorithm uses the bruteforce algorithm because we
     * we're not able to had the non free features.
     * @param descriptors1 The feature points (descriptors) of the first image.
     * @param descriptors2 The feature points (descriptors) of the first image.
     * @return A matrix of matching descriptors.
     */
    private MatOfDMatch matchDescriptors(Mat descriptors1, Mat descriptors2) {
        MatOfDMatch matches = new MatOfDMatch();
        DescriptorMatcher dm = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        dm.match(descriptors1, descriptors2, matches);
        return matches;
    }

    /**
     * Calculate the minimum distance between the matches.
     * @param matches Matrix of matches.
     * @return The minimum distance.
     */
    private double calculateMinDistanceBetweenMatch(MatOfDMatch matches) {
        double minDistance = 100;

        DMatch[] matchesArr = matches.toArray();

        for (DMatch match: matchesArr) {
            minDistance = match.distance < minDistance ? match.distance : minDistance;
        }

        return minDistance;
    }

    /**
     * Calculate the good matches according to the minimum distance.
     * @param matches Matrix of matches.
     * @param minDistance The minimum distance.
     * @return The list of good matches.
     */
    private List<DMatch> calculateGoodMatches(MatOfDMatch matches, double minDistance) {
        List<DMatch> goodMatches = new ArrayList<DMatch>();

        DMatch[] matchesArr = matches.toArray();

        minDistance = DISTANCE_MULTIPLIER * minDistance > ABSOLUTE_MINIMAL_DISTANCE ?
                DISTANCE_MULTIPLIER * minDistance : ABSOLUTE_MINIMAL_DISTANCE;

        for (DMatch match: matchesArr) {
            if(match.distance <= minDistance) {
                goodMatches.add(match);
            }
        }

        return goodMatches;
    }
}
