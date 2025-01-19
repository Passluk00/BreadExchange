import {Component, OnInit} from '@angular/core';
import {TestRequest} from "../../../services/models/test-request";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {TestService} from "../../../services/services/test.service";
import {faSave, faTimes} from "@fortawesome/free-solid-svg-icons";
import {FormsModule} from "@angular/forms";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {NgForOf, NgIf} from "@angular/common";


@Component({
  selector: 'app-test-item',
  imports: [
    FormsModule,
    FaIconComponent,
    NgIf,
    NgForOf,
    RouterLink
  ],
  templateUrl: './test-item.component.html',
  standalone: true,
  styleUrl: './test-item.component.scss'
})
export class TestItemComponent implements OnInit{

  defaultImg:string = "./public/testImg/bread.jpeg";    // TODO da cambiare con un imagine esempio
  errorMsg: Array<string> = [];
  selectedItemCover: any;
  selectedPicture: string | undefined;



  testRequest: TestRequest = {
    name: "",
    description: "",
  }


  constructor(
    private router: Router,
    private service: TestService,
    private activatedRoute: ActivatedRoute,

  ){}

  ngOnInit(): void {}


  // metodo vecchio upload file funzionante  bisogna cambiare api

  onFileSelected(event: any) {

    this.selectedItemCover = event.target.files[0];
    console.log(this.selectedItemCover);

    if (this.selectedItemCover) {

      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      };
      reader.readAsDataURL(this.selectedItemCover);

    }
  }

  saveItem(){

    this.service.saveTest({
      body: this.testRequest
    }).subscribe({
      next:(itemId) => {
        this.service.uploadCoverPictures({
          'test-id': itemId,
          body:{
            file: this.selectedItemCover
          }
        }).subscribe({
          next:() => {
            this.router.navigate(["/homepage"]);
          }
        })
      },
      error:(err) => {
        this.errorMsg = err.error.validationErrors;
      }
    })
  }





  protected readonly faSave = faSave;
  protected readonly faTimes = faTimes;

}
