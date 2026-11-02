package com.nidza.castfromscrolls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScrollCastRulesTest {
    @Test
    void scrollsBypassLearningAndRecastRestrictions() {
        assertFalse(ScrollCastRules.learningRequired(true, true));
        assertEquals(0, ScrollCastRules.recastCount(true, 3));
        assertTrue(ScrollCastRules.retainScroll(true));
    }

    @Test
    void otherCastSourcesKeepOriginalRules() {
        assertTrue(ScrollCastRules.learningRequired(false, true));
        assertFalse(ScrollCastRules.learningRequired(false, false));
        assertEquals(3, ScrollCastRules.recastCount(false, 3));
        assertFalse(ScrollCastRules.retainScroll(false));
    }
}
