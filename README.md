# 2023 Varsity Robot Code 

## Team 1810 - Catatronics - code for the 2023 competition season

### Bot yet unnamed

---
### Controller Bindings

#### Josh's Controller

| Button        | Purpose            |
| -----         | -----              |
| Red Switch H  | Intake - in        |
| Red Switch L  | Intake - out       |
| Blue Switch   |                    |
| Rotary 0-5    | Arm Positioning    |

#### Joystick

| Button         | Purpose   |
| -----          | -----     |
| Trigger        |           |
| 1              |           |
| 2              |           |
| 3              |           |
| 4              |           |
| 5              |           |
| 6              |           |
| 7              |           |
| 8              |           |
| 9              | Zero Gyro |
| 10             |           |
| 11             |           |

---

#### CAN

| ID     | Mechanism                    | Being Controlled   | Controller |
| -----  | -----                        | -----              | -----      |
| 01     | Drive - Front Right Steer    | Falcon 500         | TalonFX    |
| 02     | Drive - Front Right Drive    | Falcon 500         | TalonFX    |
| 03     | Drive - Front Left Steer     | Falcon 500         | TalonFX    |
| 04     | Drive - Front Left Drive     | Falcon 500         | TalonFX    |
| 05     | Drive - Back Right Steer     | Falcon 500         | TalonFX    |
| 06     | Drive - Back Right Drive     | Falcon 500         | TalonFX    |
| 07     | Drive - Back Left Steer      | Falcon 500         | TalonFX    |
| 08     | Drive - Back Left Drive      | Falcon 500         | TalonFX    |
| 09     | Drive - Front Right CANCoder |                    |            |
| 10     | Drive - Front Left CANCoder  |                    |            |
| 11     | Drive - Back Right CANCoder  |                    |            |
| 12     | Drive - Back Left CANCoder   |                    |            |
| 13     | Gyro - Pigeon 2              |                    |            |
| 14     | Intake                       | NEO 550            | SparkMAX   |
| 15     | Arm                          | RS775 W/ PG71      | SparkMAX   |

#### Relay

| Port |  Mechanism            | Being Controlled   | Controller  |
| -----| -----                 | -----              | -----       |
| 00   | Arm - Extender        | Snowblower Motor   | SPIKE       |
| 01   |                       |                    |             |
| 02   |                       |                    |             |
| 03   |                       |                    |             |

#### PWM

| Port  | Mechanism | Being Controlled | Controller |
| ----- | -----     | -----            | -----      |
| 00    |           |                  |            |
| 01    |           |                  |            |
| 02    |           |                  |            |
| 03    |           |                  |            |
| 04    |           |                  |            |
| 05    |           |                  |            |
| 06    |           |                  |            |
| 07    |           |                  |            |
| 08    |           |                  |            |
| 09    |           |                  |            |

#### DIO

|       | Mechanism           |
| ----- | -----               |
| 00    |                     |
| 01    |                     |
| 02    | Intake - Line Break |
| 03    |                     |
| 04    | Extender - Close LS |
| 05    | Extender - Far LS   |
| 06    |                     |
| 07    |                     |
| 08    |                     |
| 09    |                     |

#### Analog In

|           | Mechanism |
| -----     | -----     |
| 00        |           |
| 01        |           |
| 02        |           |
| 03        |           |
