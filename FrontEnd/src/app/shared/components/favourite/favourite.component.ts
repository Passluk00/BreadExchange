import {Component, OnInit} from '@angular/core';
import {UserControllerService} from "../../../services/services/user-controller.service";
import {BakeryFav} from "../../../services/models/bakery-fav";
import {NgForOf, NgIf} from "@angular/common";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {FrontEndControllerService} from "../../../services/services/front-end-controller.service";
import {routes} from "../../../app.routes";
import {Router} from "@angular/router";


@Component({
  selector: 'app-favourite',
  imports: [
    NgForOf,
    NgIf,
    FaIconComponent
  ],
  templateUrl: './favourite.component.html',
  standalone: true,
  styleUrl: './favourite.component.scss'
})
export class FavouriteComponent implements OnInit{

  constructor(
    private userService: UserControllerService,
    private router: Router
  ) {
  }

  favs: BakeryFav[] = []

  ngOnInit(): void {
    this.getFav()
  }


  getFav(){
    this.userService.getFav().subscribe({
      next: (res) => {
        this.favs = res
      },
      error: (err) => {
        console.error("errore Fetch dati favs: "+err)
      }
    })
  }

  removeToFav(id: number | undefined){
    if(id != undefined){
      this.userService.remToFav({
        idBac: id
      }).subscribe({
        next:() => {
          window.location.reload()
        },
        error: (err) => {
          console.error("Fail to remove from fav "+ err)
        }
      })
    }
  }


  newOrder(id: number | undefined) {

    if(id != undefined){

      this.router.navigate(['/bakery/newOrder/'+ id]);


    }

  }
}
