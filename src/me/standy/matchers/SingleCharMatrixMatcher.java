package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents basic MetaCharTemplateMatrix interface. It supports only single template.
 * @author andrew
 *         Created by andrew on 19.12.14.
 */
public class SingleCharMatrixMatcher implements MetaCharMatrixMatcher {
    private int[][] piFunctions = null;
    private String[] sample;
    private int rows;

    /**
     * Adds template matrix to this matcher. Executes in O(template matrix size).
     * @param template matrix to add.
     * @return 0
     * @throws UnsupportedOperationException if template matrix is jagged, e.g. not matrix from mathematical standpoint
     * or if template was already added to this matcher.
     * @throws IllegalArgumentException if template matrix is null
     */
    @Override
    public int addTemplate(String[] template) throws UnsupportedOperationException, IllegalStateException, IllegalArgumentException {
        if (template == null) {
            throw new IllegalArgumentException("template matrix is null");
        }
        if (piFunctions != null) {
            throw new UnsupportedOperationException("this class doesn't support multiple templates");
        }
        rows = template.length;
        if (rows > 0) {
            int cols = template[0].length();
            for (int i = 0; i < rows; i++) {
                if (template[i].length() != cols) {
                    throw new UnsupportedOperationException("jagged matrices are not supported");
                }
            }
        }
        sample = new String[rows];
        piFunctions = new int[rows][];
        for (int i = 0; i < rows; i++) {
            sample[i] = template[i] + (char) 0;
            piFunctions[i] = PiFunctionImpl.getPiFunction(sample[i]);
        }
        return 0;
    }

    /**
     * Matches template, which was previously added to this matcher, against target matrix. Executes in O(target matrix size).
     * @param target matrix to match against.
     * @return a list of {@link me.standy.matchers.Occurrence2D} representing the result.
     * @throws IllegalArgumentException if target matrix is null
     * @throws IllegalStateException if no template matrix was added
     * @throws UnsupportedOperationException if matrix is jagged
     */
    @Override
    public List<Occurrence2D> matchTarget(String[] target) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        if (target == null) {
            throw new IllegalArgumentException("target matrix is null");
        }
        if (piFunctions == null) {
            throw new IllegalStateException("no template matrix was set");
        }
        int targetRows = target.length;
        if (targetRows > 0) {
            List<Occurrence2D> result = new ArrayList<>();
            int targetCols = target[0].length();
            for (int i = 0; i < rows; i++) {
                if (target[i].length() != targetCols) {
                    throw new UnsupportedOperationException("jagged matrices are not supported");
                }
            }


            List<Integer>[] targetSuffices = new List[targetRows];
            for (int row = 0; row < targetRows; row++) {
                for (int col = 0; col < target[row].length(); col++) {
                    char nextChar = target[row].charAt(col);
                    int currentPi = 0;
                    if (col > 0) {
                        currentPi = targetSuffices[row].get(col - 1);
                    }
                    targetSuffices[row].add(PiFunctionImpl.nextPiValue(piFunctions[row], currentPi, sample[row], nextChar));
                }
            }

            for (int col = 0; col < targetCols; col++) {
                int rowsMatched = 0;
                for (int row = 0; row < targetRows; row++) {
                    if (targetSuffices[row].get(col) == sample[row].length()) {
                        rowsMatched++;
                    } else {
                        rowsMatched = 0;
                    }
                    if (rowsMatched >= rows) {
                        result.add(new Occurrence2D(row, col, 0));
                    }
                }
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }
}
