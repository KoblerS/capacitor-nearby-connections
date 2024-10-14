// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorNearbyConnections",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorNearbyConnections",
            targets: ["CapacitorNearbyConnectionsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "CapacitorNearbyConnectionsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapacitorNearbyConnectionsPlugin"),
        .testTarget(
            name: "CapacitorNearbyConnectionsPluginTests",
            dependencies: ["CapacitorNearbyConnectionsPlugin"],
            path: "ios/Tests/CapacitorNearbyConnectionsPluginTests")
    ]
)