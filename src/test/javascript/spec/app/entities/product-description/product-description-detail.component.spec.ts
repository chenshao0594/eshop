import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/product-description/product-description-detail.component';
import { ProductDescriptionService } from '../../../../../../main/webapp/app/entities/product-description/product-description.service';
import { ProductDescription } from '../../../../../../main/webapp/app/entities/product-description/product-description.model';

describe('Component Tests', () => {

    describe('ProductDescription Management Detail Component', () => {
        let comp: ProductDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductDescriptionDetailComponent>;
        let service: ProductDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ProductDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
