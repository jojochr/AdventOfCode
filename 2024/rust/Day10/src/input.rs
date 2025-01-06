use super::unchecked::{UnsafePoint, Vector};

pub fn get_input(input: &str) -> LavaMap {
    let input = input
        .lines()
        .into_iter()
        .filter(|line| line.is_empty() == false) // only keep not empty
        .map(|line| {
            // Init with expected size
            let mut line_as_ints: Vec<u8> = vec![0; line.chars().count()];
            line.chars().enumerate().for_each(|(i, c)| {
                if c.is_numeric() == false {
                    panic!("Found invalid char: {}. Chars must be numbers 0-9", c)
                }

                line_as_ints[i] = c.to_digit(10).expect(
                    format!("Found invalid char: {}. Chars must be numbers 0-9", c).as_str(),
                ) as u8;
            });
            return line_as_ints;
        })
        .collect::<Vec<Vec<u8>>>();

    LavaMap { data: input }
}

pub struct LavaMap {
    pub data: Vec<Vec<u8>>,
}

impl LavaMap {
    /// Checks if a coordinate is on the current map and returns a value that is safe to handle
    pub fn check_point(&self, to_check: UnsafePoint) -> Option<CheckedPoint> {
        if to_check.y < 0 || self.data.len() <= to_check.y as usize {
            return None;
        }
        let parsed_y = to_check.y as usize;

        if to_check.x < 0 || self.data[parsed_y].len() <= to_check.x as usize {
            return None;
        }
        let parsed_x = to_check.x as usize;

        let point = CheckedPoint {
            x: parsed_x,
            y: parsed_y,
            belongs_to: &self,
            value: &self.data[parsed_y][parsed_x],
        };

        Some(point)
    }

    /// Gets every valid starting point
    pub fn get_start_values(&self) -> Vec<CheckedPoint> {
        let capacity = self
            .data
            .iter()
            .flat_map(|row| row.iter())
            .filter(|value| **value == 0)
            .count();

        let mut starting_positions: Vec<CheckedPoint> = Vec::with_capacity(capacity);

        self.data.iter().enumerate().for_each(|(y, row)| {
            row.iter().enumerate().for_each(|(x, value)| {
                if *value != 0 {
                    return;
                }

                let point = CheckedPoint {
                    x,
                    y,
                    belongs_to: &self,
                    value,
                };

                starting_positions.push(point);
            })
        });

        starting_positions
    }
}

pub struct CheckedPoint<'a> {
    /// X-Coordinate guaranteed to be valid for the map referenced in belongs_to
    pub x: usize,
    /// Y-Coordinate guaranteed to be valid for the map referenced in belongs_to
    pub y: usize,
    pub value: &'a u8,
    /// The map that originally created this point
    pub belongs_to: &'a LavaMap,
}

impl CheckedPoint<'_> {
    pub fn is_peak(&self) -> bool {
        *self.value == 9
    }

    pub fn add_vector(&self, vector: &Vector) -> UnsafePoint {
        UnsafePoint {
            x: self.x as i64 + vector.x,
            y: self.y as i64 + vector.y,
        }
    }
}
