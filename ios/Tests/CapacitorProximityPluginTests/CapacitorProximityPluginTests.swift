import XCTest
@testable import CapacitorProximityPlugin

class CapacitorProximityPluginTests: XCTestCase {
    func testGetPluginVersion() {
        let implementation = CapacitorProximity()
        XCTAssertEqual("8.0.0", implementation.getPluginVersion())
    }
}
