INSERT INTO users (id, first_name, last_name, email, password, role)
VALUES (1, 'Admin', 'User', 'admin@example.com', '$2a$12$D6Ur0MJT0klFuclfl35s5eUo/krWuR2eQ3qhKnyy92PPbyXzTvguy', 'ADMIN'),
       (2, 'John', 'Doe', 'john.doe@example.com', '$2a$12$D6Ur0MJT0klFuclfl35s5eUo/krWuR2eQ3qhKnyy92PPbyXzTvguy', 'USER'),
       (3, 'Jane', 'Smith', 'jane.smith@example.com', '$2a$12$D6Ur0MJT0klFuclfl35s5eUo/krWuR2eQ3qhKnyy92PPbyXzTvguy', 'USER'),
       (4, 'Alice', 'Johnson', 'alice.johnson@example.com', '$2a$12$D6Ur0MJT0klFuclfl35s5eUo/krWuR2eQ3qhKnyy92PPbyXzTvguy', 'USER'),
       (5, 'Bob', 'Brown', 'bob.brown@example.com', '$2a$12$D6Ur0MJT0klFuclfl35s5eUo/krWuR2eQ3qhKnyy92PPbyXzTvguy', 'USER');

INSERT INTO tasks (id, title, description, status, priority, author_id, assignee_id)
VALUES (1, 'Fix Login Issue', 'Resolve the login bug reported by QA', 'TO_DO', 'HIGH', 1, 2),
       (2, 'Update Documentation', 'Add details for the new feature in the user guide', 'IN_PROGRESS', 'AVERAGE', 2, 3),
       (3, 'Code Review', 'Review the PR for the payment gateway', 'TO_DO', 'LOW', 3, 4),
       (4, 'Add Logging', 'Implement detailed logging for error tracking', 'COMPLETED', 'HIGH', 4, 5),
       (5, 'Optimize Query', 'Improve performance of the user query', 'TO_DO', 'AVERAGE', 5, 1),
       (6, 'Setup CI/CD', 'Automate the deployment process', 'TO_DO', 'HIGH', 1, 3),
       (7, 'Design Database Schema', 'Draft a schema for the reporting module', 'IN_PROGRESS', 'HIGH', 2, 4),
       (8, 'Prepare Test Cases', 'Write test cases for the new module', 'TO_DO', 'AVERAGE', 3, 5),
       (9, 'Implement Caching', 'Add Redis caching to improve performance', 'TO_DO', 'HIGH', 4, 2),
       (10, 'Fix API Timeout', 'Resolve timeout issues in the payment API', 'IN_PROGRESS', 'AVERAGE', 5, 3),
       (11, 'Enhance Security', 'Upgrade authentication to use OAuth2', 'COMPLETED', 'HIGH', 1, 4),
       (12, 'Create Landing Page', 'Design and develop the new landing page', 'TO_DO', 'AVERAGE', 2, 5),
       (13, 'Run Load Tests', 'Test the system for high traffic scenarios', 'TO_DO', 'LOW', 3, 1),
       (14, 'Migrate Database', 'Move the database to a new server', 'IN_PROGRESS', 'HIGH', 4, 3),
       (15, 'Bug Fix - UI', 'Resolve overlapping elements on the dashboard', 'COMPLETED', 'LOW', 5, 2),
       (16, 'Generate Reports', 'Create monthly sales reports', 'TO_DO', 'AVERAGE', 1, 5),
       (17, 'Refactor Code', 'Improve readability and remove duplicate code', 'IN_PROGRESS', 'LOW', 2, 1),
       (18, 'Add Notifications', 'Enable real-time notifications', 'TO_DO', 'HIGH', 3, 4),
       (19, 'Integrate Payment Gateway', 'Add support for a new payment provider', 'TO_DO', 'HIGH', 4, 1),
       (20, 'Deploy to Production', 'Release the new version of the app', 'TO_DO', 'AVERAGE', 5, 3);

INSERT INTO comments (id, content, task_id, user_id)
VALUES
-- Comments for Task 1
(1, 'Investigating the issue.', 1, 2),
(2, 'Found a potential root cause.', 1, 3),
(3, 'Please check the logs for more details.', 1, 4),

-- Comments for Task 2
(4, 'Added documentation for the API.', 2, 3),
(5, 'Reviewed the changes and approved.', 2, 5),
(6, 'Please clarify a few points.', 2, 1),

-- Comments for Task 3
(7, 'Code looks clean. Good job!', 3, 4),
(8, 'Consider adding more test cases.', 3, 5),
(9, 'Updated the review with comments.', 3, 2),

-- Comments for Task 4
(10, 'Implemented logging as requested.', 4, 3),
(11, 'Logs are now more detailed.', 4, 4),
(12, 'Tested the feature locally.', 4, 1),

-- Comments for Task 5
(13, 'Optimized the query using indexes.', 5, 2),
(14, 'Reduced query execution time by 30%.', 5, 5),
(15, 'Tested with sample data successfully.', 5, 3),
(16, 'Ready for review.', 5, 1),

-- Comments for Task 6
(17, 'Configured CI/CD pipeline.', 6, 2),
(18, 'Deployment automation working perfectly.', 6, 4),
(19, 'Added a few tweaks to the scripts.', 6, 5),

-- Comments for Task 7
(20, 'Drafted the schema for review.', 7, 1),
(21, 'Suggestions for better normalization.', 7, 3),
(22, 'Please address the comments.', 7, 5),

-- Comments for Task 8
(23, 'Test cases for edge scenarios added.', 8, 2),
(24, 'Identified a few missing cases.', 8, 3),
(25, 'Tests passed successfully.', 8, 4),

-- Comments for Task 9
(26, 'Redis caching implemented successfully.', 9, 1),
(27, 'Tested performance improvement.', 9, 3),
(28, 'Results are promising.', 9, 4),
(29, 'Ready for deployment.', 9, 5),

-- Comments for Task 10
(30, 'Fixed the timeout issue.', 10, 2),
(31, 'Tested with large datasets.', 10, 4),
(32, 'Issue seems resolved.', 10, 5),
(33, 'Awaiting final review.', 10, 1);
