
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PricelistService} from '../service/pricelist.service';
import {RoomService} from "../service/room.service";

@Component({
  templateUrl: './pricelist.component.html'
})

export class PricelistComponent implements OnInit{
  items=[];
  roomPrice: boolean;
  constructor(protected  router: Router,
              private route: ActivatedRoute,
              private priceService: PricelistService,
              private roomService: RoomService,){

  }

  ngOnInit(){
    const idR = this.route.snapshot.params.idR;
    const idA = this.route.snapshot.params.idA;

    this.roomService.getOneRoom(idA, idR).subscribe(data => {

        this.roomPrice = data.active;

    })
    this.priceService.getPriceForRoom(idR).subscribe(data => {
      this.items = data;
    })


  }
  addPriceList(){
    const idR = this.route.snapshot.params.idR;
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/'+ idA + '/pricelist/add/' + idR );
  }
  goBack(){
    const idA = this.route.snapshot.params.idA;
    this.router.navigateByUrl('welcomepage/room/'+ idA );
  }

}
