import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { NgbModal, NgbModalRef, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , AlertService, JhiLanguageService  } from 'ng-jhipster';

import { ProductOption, ProductOptionValue } from './product-option.model';
import { ProductOptionService } from './product-option.service';

@Component({
    selector: 'jhi-product-option-detail',
    templateUrl: './product-option-detail.component.html'
})
export class ProductOptionDetailComponent implements OnInit, OnDestroy {

    productOptionValue: ProductOptionValue;
    productOptionId : number;
    productOption: ProductOption;
    productOptionValues: ProductOptionValue[];
    private subscription: any;
    private eventSubscriber: Subscription;
    modalRef: NgbModalRef;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productOptionService: ProductOptionService,
        private route: ActivatedRoute,
        private modalService: NgbModal,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productOption']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            
        });
        this.productOptionValue = new ProductOptionValue();
        this.registerChangeInProductOptionValues();
    }

    
    load(id) {
        this.productOptionService.find(id).subscribe((productOption) => {
            this.productOption = productOption;
            this.productOptionValues = this.productOption.productOptionValues;
        });
    }
    loadOptionValues(optionId){
        this.productOptionService.queryOptionValue(optionId).subscribe(
                (res: Response) => { this.productOptionValues = res.json(); }, 
                (res: Response) => this.onError(res.json()));
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductOptionValues() {
        this.eventSubscriber = this.eventManager.subscribe('optionValueListModification', 
                    (response) => this.loadOptionValues(this.productOption.id));
    }
    
    createOptionValue(content) {
        this.modalRef = this.modalService.open(content);
        this.modalRef.result.then((result) => {
          }, (reason) => {
          });
      }
    
    saveOptionValue(){
        this.productOptionService.createOptionValue(this.productOption.id, this.productOptionValue)
            .subscribe((res: ProductOption) =>this.onSaveSuccess(res), 
                        (res: Response) => this.onSaveError(res));
    }
    
    private onSaveSuccess(result: ProductOption) {
        this.modalRef.close();
        this.eventManager.broadcast({ name: 'optionValueListModification', content: 'OK'});
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            alert(error);
            error.message = error.text();
        }
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
