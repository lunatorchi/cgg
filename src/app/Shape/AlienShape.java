package app.shape;

import app.material.Material;
import cgg_tools.Mat4x4;
import cgg_tools.Vec3;

/**
 * Creature with joints
 */
public class AlienShape implements Shape {
    
    private GroupShape creature;
    
    /**
     * creates our funky dancers
     * @param bodyMaterial Material for body parts -> cylinders
     * @param jointMaterial Material for our joints
     * @param headMaterial Material for head
     * @param hatMaterial Material für Hat
     * @param footMaterial Material für FFeet
     * @param headSize SIze of Head
     * @param legLength Lenght of legs
     * @param legThickness Thikness of legs
     * @param leftHipAngle angle of left hip angle
     * @param leftKneeAngle angle of left knee
     * @param leftAnkleAngle angle left ankle
     * @param rightHipAngle angle des right hip
     * @param rightKneeAngle angle right knee 
     * @param rightAnkleAngle angle right ankle
     */
    public AlienShape(
        Material bodyMaterial,
        Material jointMaterial,
        Material headMaterial,
        Material hatMaterial,
        Material footMaterial,
        double headSize,
        double legLength,
        double legThickness,
        double leftHipAngle,
        double leftKneeAngle,
        double leftAnkleAngle,
        double rightHipAngle,
        double rightKneeAngle,
        double rightAnkleAngle
    ) {
        
        double jointSize = headSize * 0.15;
        double footHeight = headSize * 0.3;
        double footLength = headSize * 0.5;
        
        /* HEAD and HAT */
        SphereShape head = new SphereShape(new Vec3(0, 0, 0), headSize, headMaterial);
        
        // HAT
        double hatBrimRadius = headSize * 1.2; // Hat rim slightly bigger than head
        double hatHeight = headSize * 3.5; // veryy long hat
        double hatRadius = headSize * 0.4; // thin hat
        
        /* create brim with discshape */
        DiscShape hatBrim = new DiscShape(
            new Vec3(0, headSize, 0), 
            hatBrimRadius, 
            hatMaterial
        );
        
        /* create Hat cylinder with closedCylinderShape */
        ClosedCylinderShape hatCylinder = new ClosedCylinderShape(
            hatRadius, 
            0, 
            hatHeight, 
            hatMaterial, 
            hatMaterial
        );
        
        /* move hat ontop of head */
        Mat4x4 hatTopTransform = Mat4x4.move(0, headSize, 0);
        GroupShape transformedHatTop = new GroupShape(hatTopTransform, hatCylinder);
        
        /* LEFT LEG */
        double hipY = -headSize; // Hip directly under Head
        double leftHipX = -headSize * 0.3;
        
        // LEFT HIP JOINT 
        // Transform
        Mat4x4 TransformLeftHip = Mat4x4.multiply(
            Mat4x4.move(leftHipX, hipY, 0),
            Mat4x4.rotate(0, 0, 1, leftHipAngle)
        );
        
        // SPhere left hip joint
        SphereShape leftHipJoint = new SphereShape(new Vec3(0, 0, 0), jointSize, jointMaterial);
        GroupShape leftHipSkin = new GroupShape(Mat4x4.identity(), leftHipJoint);
        
        // Upper left leg
        ClosedCylinderShape thighCylinder = new ClosedCylinderShape(
            legThickness,
            -legLength,
            0,
            bodyMaterial,
            bodyMaterial
        );
        GroupShape leftThighSkin = new GroupShape(Mat4x4.identity(), thighCylinder);
        
        // LEFT KNEE
        Mat4x4 TransformLeftKnee = Mat4x4.multiply(
            Mat4x4.move(0, -legLength, 0),
            Mat4x4.rotate(0, 0, 1, leftKneeAngle)
        );
        
        // left knee joint
        SphereShape leftKneeJoint = new SphereShape(new Vec3(0, 0, 0), jointSize, jointMaterial);
        GroupShape leftKneeSkin = new GroupShape(Mat4x4.identity(), leftKneeJoint);
        
        // left shin
        GroupShape leftShinSkin = new GroupShape(Mat4x4.identity(), thighCylinder);
        
        // LEFT ANKLE
        Mat4x4 TransformLeftAnkle = Mat4x4.multiply(
            Mat4x4.move(0, -legLength, 0),
            Mat4x4.rotate(0, 0, 1, leftAnkleAngle)
        );
        
        // left ankle joint
        SphereShape leftAnkleJoint = new SphereShape(new Vec3(0, 0, 0), jointSize, jointMaterial);
        GroupShape leftAnkleSkin = new GroupShape(Mat4x4.identity(), leftAnkleJoint);
        
        // left foot
        CuboidShape footCuboid = new CuboidShape(
            new Vec3(0, -footHeight * 0.5, footLength * 0.3),
            new Vec3(footLength * 0.6, footHeight, footLength),
            footMaterial
        );
        GroupShape leftFootSkin = new GroupShape(Mat4x4.identity(), footCuboid);
        
        // left ankle group
        GroupShape leftAnkleGroup = new GroupShape(
            TransformLeftAnkle,
            leftAnkleSkin,
            leftFootSkin
        );
        
        // left knee group
        GroupShape leftKneeGroup = new GroupShape(
            TransformLeftKnee,
            leftKneeSkin,
            leftShinSkin,
            leftAnkleGroup
        );
        
        // left hip group
        GroupShape leftHipGroup = new GroupShape(
            TransformLeftHip,
            leftHipSkin,
            leftThighSkin,
            leftKneeGroup
        );
        
        // RIGHT HIP
        double rightHipX = headSize * 0.3;
        
        Mat4x4 TransformRightHip = Mat4x4.multiply(
            Mat4x4.move(rightHipX, hipY, 0),
            Mat4x4.rotate(0, 0, 1, -rightHipAngle)
        );
        
        // right hip joint
        SphereShape rightHipJoint = new SphereShape(new Vec3(0, 0, 0), jointSize, jointMaterial);
        GroupShape rightHipSkin = new GroupShape(Mat4x4.identity(), rightHipJoint);

        // right thigh
        GroupShape rightThighSkin = new GroupShape(Mat4x4.identity(), thighCylinder);
        
        // RIGHT KNEE
        Mat4x4 TransformRightKnee = Mat4x4.multiply(
            Mat4x4.move(0, -legLength, 0),
            Mat4x4.rotate(0, 0, 1, -rightKneeAngle)
        );
        
        // right knee joint
        SphereShape rightKneeJoint = new SphereShape(new Vec3(0, 0, 0), jointSize, jointMaterial);
        GroupShape rightKneeSkin = new GroupShape(Mat4x4.identity(), rightKneeJoint);
        // right shin
        GroupShape rightShinSkin = new GroupShape(Mat4x4.identity(), thighCylinder);
        
        // RIGHT ANKLE
        Mat4x4 TransformRightAnkle = Mat4x4.multiply(
            Mat4x4.move(0, -legLength, 0),
            Mat4x4.rotate(0, 0, 1, -rightAnkleAngle)
        );
        
        // right ankle joint
        SphereShape rightAnkleJoint = new SphereShape(new Vec3(0, 0, 0), jointSize, jointMaterial);
        GroupShape rightAnkleSkin = new GroupShape(Mat4x4.identity(), rightAnkleJoint);
        // right foot
        GroupShape rightFootSkin = new GroupShape(Mat4x4.identity(), footCuboid);
        
        // right ankle group
        GroupShape rightAnkleGroup = new GroupShape(
            TransformRightAnkle,
            rightAnkleSkin,
            rightFootSkin
        );
        
        // right knee group
        GroupShape rightKneeGroup = new GroupShape(
            TransformRightKnee,
            rightKneeSkin,
            rightShinSkin,
            rightAnkleGroup
        );
        
        // right hip group
        GroupShape rightHipGroup = new GroupShape(
            TransformRightHip,
            rightHipSkin,
            rightThighSkin,
            rightKneeGroup
        );
    
        /* Join everything together to form our figur */
        this.creature = new GroupShape(
            Mat4x4.identity(),
            head,
            hatBrim,
            transformedHatTop,
            leftHipGroup,
            rightHipGroup
        );
    }
    
    @Override
    public app.Hit intersect(app.Ray ray) {
        return creature.intersect(ray);
    }
}