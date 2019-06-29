
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RoomService} from '../service/room.service';

@Component ({
  templateUrl: './room.component.html',

})

export  class RoomComponent implements OnInit{
  items=[]
  comment: any;
  idRoom: any;
  message = '';




  constructor(protected  router: Router,
              private route: ActivatedRoute,
              private roomService: RoomService){}
  ngOnInit(){
    const idAccomodation = this.route.snapshot.params.idA;

    this.roomService.getAllRoom(idAccomodation).subscribe(data =>{
      this.items = data;
    })



  }

  /*getComment(idRoom: any) {
      this.roomService.getRoomRating(idRoom).subscribe(data =>{
        alert(data);
      })

  }*/

  addRoom(){
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/' +  idA  + '/add/');
  }
  editRoom(id: any){
    const idAccomodation = this.route.snapshot.params.idA;

    this.roomService.checkIfReservedRoom(id).subscribe(data => {
      if(data == false){
        this.router.navigateByUrl('welcomepage/room/' + idAccomodation + '/edit/' + id );
      }else{
        alert('Soba je rezervisana!');
      }
    })
  }
  deleteRoom(id: any){
    const idAccomodation = this.route.snapshot.params.idA;

    this.roomService.checkIfReservedRoom(id).subscribe(data => {
      if (data == false) {
        this.roomService.deleteRoom(idAccomodation , id).subscribe(data => {
          alert('Soba je uspesno obrisana');
          this.router.navigateByUrl('welcomepage/room/' + idAccomodation);
        })
        location.reload();
      } else {
        alert('Soba je rezervisana!');

      }

    })
  }
  priceList(id: any){
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/' + idA + '/pricelist/' + id );

}
  goBack(){
    this.router.navigateByUrl('welcomepage' );

  }
}
