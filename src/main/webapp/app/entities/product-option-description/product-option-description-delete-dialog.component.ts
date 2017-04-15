import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductOptionDescription } from './product-option-description.model';
import { ProductOptionDescriptionPopupService } from './product-option-description-popup.service';
import { ProductOptionDescriptionService } from './product-option-description.service';

@Component({
    selector: 'jhi-product-option-description-delete-dialog',
    templateUrl: './product-option-description-delete-dialog.component.html'
})
export class ProductOptionDescriptionDeleteDialogComponent {

    productOptionDescription: ProductOptionDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productOptionDescriptionService: ProductOptionDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productOptionDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productOptionDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productOptionDescriptionListModification',
                content: 'Deleted an productOptionDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-option-description-delete-popup',
    template: ''
})
export class ProductOptionDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionDescriptionPopupService: ProductOptionDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productOptionDescriptionPopupService
                .open(ProductOptionDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
