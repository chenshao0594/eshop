import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { TaxRate } from './tax-rate.model';
import { TaxRatePopupService } from './tax-rate-popup.service';
import { TaxRateService } from './tax-rate.service';

@Component({
    selector: 'jhi-tax-rate-delete-dialog',
    templateUrl: './tax-rate-delete-dialog.component.html'
})
export class TaxRateDeleteDialogComponent {

    taxRate: TaxRate;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taxRateService: TaxRateService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taxRate']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taxRateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'taxRateListModification',
                content: 'Deleted an taxRate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tax-rate-delete-popup',
    template: ''
})
export class TaxRateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taxRatePopupService: TaxRatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.taxRatePopupService
                .open(TaxRateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
