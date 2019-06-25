import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ReservationBackModel} from '../model/reservationBack.model';
import {ReservationService} from '../service/reservation.service';

@Component({
  selector: 'app-list-reservation',
  templateUrl: './list-reservation.component.html',
  styleUrls: ['./list-reservation.component.css']
})
export class ListReservationComponent implements OnInit {

  public reservations: ReservationBackModel[];

  constructor(protected router: Router, private data: ReservationService) { }

  ngOnInit() {

      this.data.getMyRes().subscribe(data => {
        this.reservations = data;
      });
  }

  cancelRes(id: number) {
    this.data.cancelRes(id).subscribe(data => {
      window.location.reload();
    });
  }

  back() {
    this.router.navigateByUrl('');
  }
}
