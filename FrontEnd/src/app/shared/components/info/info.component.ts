import {Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {UserControllerService} from "../../../services/services/user-controller.service";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {faArrowLeft, faEnvelope, faLocationDot, faPencil, faSliders, faUser} from "@fortawesome/free-solid-svg-icons";
import {isPlatformBrowser} from "@angular/common";

@Component({
  selector: 'app-info',
  imports: [
    FaIconComponent
  ],
  templateUrl: './info.component.html',
  standalone: true,
  styleUrl: './info.component.scss'
})
export class InfoComponent implements OnInit{

  constructor(
    private userService: UserControllerService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }


  data:any = {}
  add:any;


  ngOnInit(): void {
    this.getDati()

  }





  getDati(){
    if(isPlatformBrowser(this.platformId)) {
      this.userService.getUserInfo().subscribe({
        next: (res) => {
          if (res) {
            this.data = res;
            this.add = res.address
            console.log("Fetch dati eseguito: ", this.data)
          }
        },
        error: () => {
          console.log("Fetch dati Fallito: ")
        }
      })
    }

  }




  protected readonly faUser = faUser;
  protected readonly faEnvelope = faEnvelope;
  protected readonly faLocationDot = faLocationDot;

}
