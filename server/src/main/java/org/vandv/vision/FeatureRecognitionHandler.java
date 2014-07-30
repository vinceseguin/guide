package org.vandv.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.vandv.vision.FeatureCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinceseguin on 16/07/14.
 */
public class FeatureRecognitionHandler extends ImageRecognitionHandler {

    @Override
    public boolean handleRequest(Mat image1, Mat image2) {

        MatOfDMatch matches = matchDescriptors(calculateDescriptors(image1), calculateDescriptors(image2));

        List<DMatch> goodMatches = calculateGoodMatches(matches, calculateMinDistanceBetweenMatch(matches));

        return goodMatches.size() / matches.rows() >= 0.5 && successor.handleRequest(image1, image2);
    }

    private Mat calculateDescriptors(Mat image) {
        FeatureCalculator calculator = new FeatureCalculator(image);
        return calculator.calculate();
    }

    private MatOfDMatch matchDescriptors(Mat descriptors1, Mat descriptors2) {
        MatOfDMatch matches = new MatOfDMatch();
        DescriptorMatcher dm = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        dm.match(descriptors1, descriptors2, matches);
        return matches;
    }

    private double calculateMinDistanceBetweenMatch(MatOfDMatch matches) {
        double minDistance = 100;

        DMatch[] matchesArr = matches.toArray();

        for (DMatch match: matchesArr) {
            minDistance = match.distance < minDistance ? match.distance : minDistance;
        }

        return minDistance;
    }

    private List<DMatch> calculateGoodMatches(MatOfDMatch matches, double minDistance) {
        List<DMatch> goodMatches = new ArrayList<DMatch>();

        DMatch[] matchesArr = matches.toArray();

        minDistance = 2 * minDistance > 0.02 ? 2 * minDistance : 0.02;
        for (DMatch match: matchesArr) {
            if(match.distance <= minDistance) {
                goodMatches.add(match);
            }
        }

        return goodMatches;
    }
}
