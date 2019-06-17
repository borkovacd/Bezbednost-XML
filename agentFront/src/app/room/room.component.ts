
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RoomService} from '../service/room.service';

@Component ({
  templateUrl: './room.component.html',

})

export  class RoomComponent implements OnInit{
  items=[]

  constructor(protected  router: Router,
              private route: ActivatedRoute,
              private roomService: RoomService){}
  ngOnInit(){
    const idAccomodation = this.route.snapshot.params.idA;

    this.roomService.getAllRoom(idAccomodation).subscribe(data =>{
      this.items = data;
    })

  }

  addRoom(){
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/' +  idA  + '/add' );
  }
  editRoom(id: any){
  }
  deleteRoom(id: any){

  }
  priceList(id: any){
    this.router.navigateByUrl('welcomepage/room/' + id + '/pricelist');

}
}
