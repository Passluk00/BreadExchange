import {Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {ActivatedRoute, Route, Router} from "@angular/router";
import {CaroselloInfinitoComponent} from "../../components/carosello-infinito/carosello-infinito.component";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {BakeryfrontEndResponse} from "../../../../services/models/bakeryfront-end-response";
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {FooterComponent} from "../../../components/footer/footer.component";
import {AddressComponent} from "../../components/address/address.component";
import {Address} from "../../../../services/models/address";
import {OrariComponent} from "../../components/orari/orari.component";
import {faArrowLeft, faPencil} from "@fortawesome/free-solid-svg-icons";
import {CodeInputModule} from "angular-code-input";
import {FormsModule} from "@angular/forms";
import {BakeryControllerService} from "../../../../services/services/bakery-controller.service";
import {NewAddress} from "../../../../services/models/new-address";
import {Week} from "../../../../services/models/week";
import {WeekDay} from "../../../../services/models/week-day";
import {ModifyWeekRequest} from "../../../../services/models/modify-week-request";
import {ToolBarComponent} from "../../components/tool-bar/tool-bar.component";
import {isPlatformBrowser, NgIf} from "@angular/common";
import {UserControllerService} from "../../../../services/services/user-controller.service";
import {AuthenticationService} from "../../../../services/services/authentication.service";

@Component({
  selector: 'app-bakery',
    imports: [
        CaroselloInfinitoComponent,
        NavbarComponent,
        FooterComponent,
        AddressComponent,
        OrariComponent,
        CodeInputModule,
        FormsModule,
        ToolBarComponent
    ],
  standalone: true,
  templateUrl: '/bakery.component.html',
  styleUrl: './bakery.component.scss'
})
export class BakeryComponent implements OnInit{


  userId: number | undefined;
  data: BakeryfrontEndResponse = {}
  toPass: Address = {}
  isOwner: boolean = false;
  isLogged: boolean = false;
  currentWeek: Week = {}
  lun: WeekDay = {
    dayOfWeek:"Lunedi"
  }
  mar: WeekDay = {
    dayOfWeek:"Martedi"
  }
  mer: WeekDay = {
    dayOfWeek:"Mercoledi"
  }
  gio: WeekDay = {
    dayOfWeek:"Giovedi"
  }
  ven: WeekDay = {
    dayOfWeek:"Venerdi"
  }
  sab: WeekDay = {
    dayOfWeek:"Sabato"
  }
  dom: WeekDay = {
    dayOfWeek:"Domenica"
  }


  constructor(
    private route: ActivatedRoute,
    private frontEndService: FrontEndControllerService,
    private authService: AuthenticationService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params =>{
      this.userId = +params['id']
      this.checkOwner()
      this.getData()
      this.getWeek()
      this.checkIsLogged()
    })
  }


  goToNewOrder(){
    if(this.userId != undefined ){
      if(this.isLogged) {
        this.router.navigate([`/bakery/newOrder/${this.userId}`]);
      }else{
        this.router.navigate([`/login`]);
      }

    }
  }

  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){
      return localStorage.getItem("token")
    }
    return null
  }

  checkIsLogged(){
      if(isPlatformBrowser(this.platformId)) {
      const toc = this.getToken()

      if(toc) {
        this.authService.check({
          token: toc
        }).subscribe({
          next: (res) => {
            this.isLogged = res;
          },
          error: (err) => {
            console.error("Errore nel verificare se sei loggato: " + err)
          }
        })
      }

    }
  }


  getData(){
    if(this.userId) {
      this.frontEndService.bakeryDetail({
        id: this.userId
      }).subscribe({
        next: (res) => {
          this.data = res;
          if(this.data.address) {
            this.toPass = this.data.address;
          }
        },
        error: (err) => {
          console.error("Errore nel Fetch Dati: " + err)
        }
      })
    }
  }

  getWeek(){

    if(this.userId != null){

      this.frontEndService.getWeek({
        idBakery: this.userId
      }).subscribe({
        next: (res) => {
          this.currentWeek = res
          this.aggiornaDati(res);
        },
        error: (err) => {
          console.error("errore nel fetch della week")
        }
      })

    }

  }

  aggiornaDati(res: Week){

    if( res.lun != undefined &&
        res.mar != undefined &&
        res.mer != undefined &&
        res.gio != undefined &&
        res.ven != undefined &&
        res.sab != undefined &&
        res.dom !=undefined) {

      this.lun = res.lun
      this.mar = res.mar
      this.mer = res.mer
      this.gio = res.gio
      this.ven = res.ven
      this.sab = res.sab
      this.dom = res.dom

    }

  }

  checkOwner(){
    if(this.userId){
      this.frontEndService.checkOwner({
        idBack: this.userId
      }).subscribe({
        next:(res) => {
          this.isOwner = res;
          console.error("valore owner: "+this.isOwner)
        },
        error:() => {
          this.isOwner = false
        }
      })
    }
  }


  protected readonly faArrowLeft = faArrowLeft;
  protected readonly faPencil = faPencil;
}
