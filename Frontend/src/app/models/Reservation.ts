import { Space } from "./Space"
import { User } from "./User"

export class Reservation{
id?:number
user?:User
space?:Space
startTime?:Date
endTime?:Date
}