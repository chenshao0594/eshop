import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { DigitalProduct } from './digital-product.model';
import { DigitalProductPopupService } from './digital-product-popup.service';
import { DigitalProductService } from './digital-product.service';

@Component({
    selector: 'jhi-digital-product-delete-dialog',
    templateUrl: './digital-product-delete-dialog.component.html'
})
export class DigitalProductDeleteDialogComponent {

    digitalProduct: DigitalProduct;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private digitalProductService: DigitalProductService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['digitalProduct']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.digitalProductService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'digitalProductListModification',
                content: 'Deleted an digitalProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-digital-product-delete-popup',
    template: ''
})
export class DigitalProductDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private digitalProductPopupService: DigitalProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.digitalProductPopupService
                .open(DigitalProductDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
