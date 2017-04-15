import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Language } from './language.model';
import { LanguagePopupService } from './language-popup.service';
import { LanguageService } from './language.service';

@Component({
    selector: 'jhi-language-delete-dialog',
    templateUrl: './language-delete-dialog.component.html'
})
export class LanguageDeleteDialogComponent {

    language: Language;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private languageService: LanguageService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['language']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.languageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'languageListModification',
                content: 'Deleted an language'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-language-delete-popup',
    template: ''
})
export class LanguageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private languagePopupService: LanguagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.languagePopupService
                .open(LanguageDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
