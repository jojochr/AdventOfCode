pub struct UnsafePoint {
    pub x: i64,
    pub y: i64,
}

impl UnsafePoint {
    pub fn add_vector(&mut self, vector: &Vector) -> UnsafePoint {
        UnsafePoint {
            x: self.x + vector.x,
            y: self.y + vector.y,
        }
    }
}

#[derive(Clone, Copy, Debug)]
pub struct Vector {
    pub x: i64,
    pub y: i64,
}

impl Vector {
    pub fn get_all_directions() -> Vec<Vector> {
        vec![
            Vector { x: 1, y: 0 },  // Right
            Vector { x: 0, y: 1 },  // Down
            Vector { x: -1, y: 0 }, // Left
            Vector { x: 0, y: -1 }, // Up
        ]
    }
}
