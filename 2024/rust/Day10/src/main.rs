use std::collections::HashSet;
use std::thread;
use std::thread::JoinHandle;

fn main() {
    println!("Start Solving Day 10:");

    let input = include_str!("../Day10-Input.txt").to_string();
    let input = get_input(input);

    let score = process_part1(&input);

    println!("Solution for Part 1 is: \"{}\"", score);
}

fn process_part1(map: &LavaMap) -> u64 {
    let mut handles: Vec<JoinHandle<u64>> = Vec::new();
    println!(
        "Amount of starting Positions: {}",
        map.get_starting_positions().iter().count()
    );

    map.get_starting_positions().into_iter().for_each(|p| {
        let cloned_map = map.clone();
        let mut visited_positions: HashSet<Position> = HashSet::new();

        handles.push(thread::spawn(move || {
            part1_pathfinding_recursive(p, &mut visited_positions, cloned_map)
        }));
    });

    let mut result: u64 = 0;
    for handle in handles.into_iter() {
        let result_from_thread = handle.join().unwrap();
        println!("{}", result_from_thread);
        result += result_from_thread;
    }

    result
}

fn part1_pathfinding_recursive(
    current_pos: Position,
    visited_positions: &mut HashSet<Position>,
    lava_map: LavaMap,
) -> u64 {
    // if false == visited_positions.insert(current_pos) {
    //     return 0;
    // }

    if lava_map.pos_is_peak(&current_pos) {
        return 1;
    }

    let valid_directions: Vec<Vector> = Vector::get_all_directions()
        .into_iter()
        .filter(|direction| {
            lava_map.is_valid_target(
                &(current_pos.x as i64 + direction.x as i64),
                &(current_pos.y as i64 + direction.y as i64),
            )
        }).collect();

    let mut unique_peaks: u64 = 0;

    for direction in valid_directions {
        let next_pos = current_pos.apply_checked_direction(direction);
        
        let current_val = lava_map.value_for_pos(&current_pos);
        let next_val = lava_map.value_for_pos(&next_pos);
        
        if current_val + 1 == next_val {
            unique_peaks +=
                part1_pathfinding_recursive(next_pos, visited_positions, lava_map.clone());
        }
    }

    unique_peaks
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_process_part1() {
        let input = include_str!("../testinput.txt").to_string();
        assert_eq!(36, process_part1(&get_input(input)));
    }
}

fn get_input(input: String) -> LavaMap {
    let mut lines: Vec<Vec<u8>> = Vec::new();
    let mut max_width: i64 = 0;

    for line in input.split("\r\n") {
        if line.is_empty() {
            continue;
        }

        let mut row: Vec<u8> = Vec::new();
        line.split("").for_each(|character| {
            if false == character.trim().is_empty() {
                row.push(
                    character
                        .parse::<u8>()
                        .expect(&format!("{character} was not u8 compatible")),
                )
            }
        });

        if row.len() > max_width as usize {
            max_width = row.len() as i64
        }

        lines.push(row);
    }

    return LavaMap {
        width: max_width - 1,
        height: (lines.len() - 1) as i64,
        data: lines,
    };
}

#[derive(Clone, Debug)]
struct LavaMap {
    width: i64,  //Max value for indexing
    height: i64, //Max value for indexing
    data: Vec<Vec<u8>>,
}

impl LavaMap {
    fn is_valid_target(&self, x: &i64, y: &i64) -> bool {
        (*x >= 0 && *x <= self.width) && (*y >= 0 && *y <= self.height)
    }

    fn value_for_pos(&self, pos: &Position) -> u8 {
        self.data[pos.y as usize][pos.x as usize]
    }

    fn pos_is_peak(&self, pos: &Position) -> bool {
        if false == self.is_valid_target(&(pos.x as i64), &(pos.y as i64)) {
            return false;
        }

        if self.data[pos.y as usize][pos.x as usize] == 9 {
            return true;
        }

        false
    }

    fn get_starting_positions(&self) -> Vec<Position> {
        let mut starting_positions: Vec<Position> = Vec::new();

        for rowindex in 0..self.data.len() {
            for col in 0..self.data[rowindex].len() {
                if self.data[rowindex][col] == 0 {
                    starting_positions.push(Position {
                        x: col as u8,
                        y: rowindex as u8,
                    })
                }
            }
        }

        starting_positions
    }
}

#[derive(Clone, Copy, Debug, Eq, Hash, PartialEq)]
struct Position {
    x: u8,
    y: u8,
}

impl Position {
    fn apply_checked_direction(&self, direction: Vector) -> Position {
        Position {
            x: (self.x as i16 + direction.x as i16) as u8,
            y: (self.y as i16 + direction.y as i16) as u8,
        }
    }
}

// First value -> x
// Second value -> y
#[derive(Clone, Copy, Debug)]
struct Vector {
    x: i8,
    y: i8,
}

impl Vector {
    fn get_all_directions() -> Vec<Vector> {
        vec![
            Vector { x: 1, y: 0 },  // Right
            Vector { x: 0, y: 1 },  // Down
            Vector { x: -1, y: 0 },  // Left
            Vector { x: 0, y: -1 }, // Up
        ]
    }
}
