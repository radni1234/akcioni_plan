/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { ProjekatDetailComponent } from 'app/entities/projekat/projekat-detail.component';
import { Projekat } from 'app/shared/model/projekat.model';

describe('Component Tests', () => {
    describe('Projekat Management Detail Component', () => {
        let comp: ProjekatDetailComponent;
        let fixture: ComponentFixture<ProjekatDetailComponent>;
        const route = ({ data: of({ projekat: new Projekat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [ProjekatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProjekatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjekatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.projekat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
