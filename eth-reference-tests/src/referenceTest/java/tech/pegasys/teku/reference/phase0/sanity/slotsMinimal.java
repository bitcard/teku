/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.teku.reference.phase0.sanity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.primitives.UnsignedLong;
import com.google.errorprone.annotations.MustBeClosed;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.apache.tuweni.junit.BouncyCastleExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.pegasys.teku.core.StateTransition;
import tech.pegasys.teku.datastructures.state.BeaconState;
import tech.pegasys.teku.ethtests.TestSuite;

@ExtendWith(BouncyCastleExtension.class)
public class slotsMinimal extends TestSuite {

  @ParameterizedTest(name = "{index}.{3} Sanity slots (Minimal)")
  @MethodSource({"sanityGenericSlotSetup"})
  void sanityProcessSlot(BeaconState pre, BeaconState post, UnsignedLong slot, String testName) {
    StateTransition stateTransition = new StateTransition();

    BeaconState state =
        assertDoesNotThrow(() -> stateTransition.process_slots(pre, pre.getSlot().plus(slot)));
    assertEquals(post, state);
  }

  @MustBeClosed
  static Stream<Arguments> sanityGenericSlotSetup() throws Exception {
    Path configPath = Paths.get("minimal", "phase0");
    Path path = Paths.get("/minimal/phase0/sanity/slots/pyspec_tests");
    return sanitySlotSetup(path, configPath);
  }
}
