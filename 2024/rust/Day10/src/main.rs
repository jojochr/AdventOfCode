use input::*;
use std::collections::HashSet;
use tokio::join;
use unchecked::*;

mod input;
mod unchecked;

#[tokio::main]
async fn main() {
    let lava_map = get_input(include_str!("../Input.txt"));

    let part_1 = solve(Part::One, &lava_map);
    let part_2 = solve(Part::Two, &lava_map);

    let (part_1, part_2) = join!(part_1, part_2);

    println!("Day 10 Part 1: {}", part_1);
    println!("Day 10 Part 2: {}", part_2);
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_if_input_is_read_correctly() {
        let should_be_input = include_str!("../Input.txt");
        // Take out line breaks
        let should_be_input = should_be_input
            .lines()
            .flat_map(|l| l.chars())
            .collect::<String>();

        // Take Map data and stretch it into a long string again
        let lava_map = get_input(include_str!("../Input.txt"));
        let output_lava_map = lava_map
            .data
            .into_iter()
            .flat_map(|row| row.into_iter())
            .map(|test: u8| test.to_string())
            .collect::<String>();

        assert_eq!(should_be_input, output_lava_map);
    }
    
    #[tokio::test]
    async fn test_program_with_test_input_part1() {
        let result = solve(Part::One, &get_input(include_str!("../Test-Input_Part1.txt"))).await;
        assert_eq!(result, 36)
    }
    
    #[tokio::test]
    async fn test_program_with_test_input_part2() {
        let result = solve(Part::Two, &get_input(include_str!("../Test-Input_Part2.txt"))).await;
        assert_eq!(result, 81)
    }
}

enum Part {
    One,
    Two,
}

async fn solve(part: Part, lava_map: &LavaMap) -> u64 {
    let start_points = lava_map.get_start_values();

    let handles = start_points
        .into_iter()
        .map(|start_point: CheckedPoint| {
            // Start an async block for every point we want to explore
            return async {
                let mut visited_positions: Option<HashSet<(usize, usize)>> = match part {
                    Part::One => Some(HashSet::new()),
                    Part::Two => None,
                };
                solve_recursive(start_point, &mut visited_positions, lava_map)
            };
        })
        .collect::<Vec<_>>();

    let mut result: u64 = 0;
    for soon_value in handles {
        result += soon_value.await;
    }
    result
}

fn solve_recursive(
    current_point: CheckedPoint,
    maybe_visited_positions: &mut Option<HashSet<(usize, usize)>>,
    lava_map: &LavaMap,
) -> u64 {
    // Check the hashset first if we have one
    match maybe_visited_positions {
        Some(visited_positions) => {
            let on_new_position = visited_positions.insert((current_point.x, current_point.y));
            if !on_new_position {
                return 0;
            }
        }
        None => (),
    }

    if current_point.is_peak() {
        return 1;
    }

    let valid_next_points: Vec<CheckedPoint> = Vector::get_all_directions()
        .into_iter()
        .map(|direction| lava_map.check_point(current_point.add_vector(&direction)))
        .filter(|maybe_point| maybe_point.is_some())
        .map(|maybe_point| maybe_point.unwrap())
        // Only keep, where the next points value is one larger
        .filter(|next_point| (current_point.value + 1) == *next_point.value)
        .collect();

    let mut unique_peaks: u64 = 0;

    for next_point in valid_next_points {
        unique_peaks += solve_recursive(next_point, maybe_visited_positions, lava_map);
    }

    unique_peaks
}
