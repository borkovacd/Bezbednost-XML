import {Room} from './room.model';

export class ReservationBackModel {
    public id: number;
    public fromDate: Date;
    public toDate: Date;
    public room: Room;
    public price: number;
}
