import { Component, OnInit, OnDestroy } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
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
    productOption: ProductOption;
    private subscription: any;
    private eventSubscriber: Subscription;

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
        this.registerChangeInProductOptions();
    }

    load(id) {
        this.productOptionService.find(id).subscribe((productOption) => {
            this.productOption = productOption;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductOptions() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionListModification', (response) => this.load(this.productOption.id));
    }
    
    createOptionValue(content) {
        this.modalService.open(content).result.then((result) => {
          }, (reason) => {
          });
      }
    
    saveOptionValue(){
        this.productOptionService.createOptionValue(this.productOption.id, this.productOptionValue)
            .subscribe((res: ProductOption) =>this.onSaveSuccess(res), 
                        (res: Response) => this.onSaveError(res));
        this.modalService.open('content').close();
    }
    
    private onSaveSuccess(result: ProductOption) {
        this.eventManager.broadcast({ name: 'productOptionListModification', content: 'OK'});
      //  this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
